package com.aircall.test.Incidents.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.aircall.test.Incidents.domain.Alarm;
import com.aircall.test.Incidents.domain.Alert;
import com.aircall.test.Incidents.domain.EscalationPolicy;
import com.aircall.test.Incidents.domain.Target;
import com.aircall.test.Incidents.domain.TargetType;
import com.aircall.test.Incidents.exception.AcknowledgedTimerNotFound;
import com.aircall.test.Incidents.port.AlertRepository;
import com.aircall.test.Incidents.port.EscPolicyService;
import com.aircall.test.Incidents.port.MailNotificationService;
import com.aircall.test.Incidents.port.SMSNotificationService;
import com.aircall.test.Incidents.port.TimerService;
import com.aircall.test.Incidents.service.api.PagerService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PagerServiceImplTest {

  private EscPolicyService policyService;

  private AlertRepository alertRepository;

  private MailNotificationService mailNotificationService;

  private SMSNotificationService smsNotificationService;

  private TimerService timerService;

  private PagerService pagerService;

  private EscalationPolicy generateEPWithEmail() {
    Target target = new Target.Builder().withId(1).withType(TargetType.EMAIL).withReference("raul.informatico.sound@gmail.com").build();
    HashMap<Integer, List<Target>> levels = new HashMap<>();
    levels.put(1, List.of(target));
    return new EscalationPolicy.Builder().withId(1).withLevels(levels).build();
  }

  private EscalationPolicy generateEPWithSMS() {
    Target target = new Target.Builder().withId(1).withType(TargetType.SMS).withReference("654123987").build();
    Target target2 = new Target.Builder().withId(1).withType(TargetType.SMS).withReference("654123989").build();
    HashMap<Integer, List<Target>> levels = new HashMap<>();
    levels.put(1, List.of(target));
    levels.put(2, List.of(target2));
    return new EscalationPolicy.Builder().withId(1).withLevels(levels).build();
  }

  private EscalationPolicy generateEPWithoutTarget() {
    HashMap<Integer, List<Target>> levels = new HashMap<>();
    levels.put(1, List.of());
    return new EscalationPolicy.Builder().withId(1).withLevels(levels).build();
  }

  @BeforeEach
  void setUp() {
    policyService = mock(EscPolicyService.class);
    alertRepository = mock(AlertRepository.class);
    mailNotificationService = mock(MailNotificationService.class);
    smsNotificationService = mock(SMSNotificationService.class);
    timerService = mock(TimerService.class);
    pagerService =
        new PagerServiceImpl(policyService, alertRepository, mailNotificationService, smsNotificationService, timerService, 30000, 1,
            "messageException");

  }

  @Test
  public void givenHealthyServiceWhenProcessAlertThenMailServiceToTargetsOfFirstLevelOfEscalationIsInvoked() {
    // arrange
    when(policyService.getEscPolicyToSpecificService(1)).thenReturn(generateEPWithEmail());
    when(timerService.createAlarm(1, Duration.ofMillis(30000))).thenReturn(
        new Alarm.Builder().withId(1).withServiceId(1).withStarted(LocalDateTime.now()).withDuration(Duration.ofMillis(30000)).build());

    // act
    pagerService.processAlert(1, "test");

    // assert
    verify(policyService).getEscPolicyToSpecificService(1);
    verify(alertRepository)
        .createOrModifyAlert(new Alert.Builder().withServiceId(1).withLevel(1).withAlarmId(1).withMessage("test").build());
    verify(timerService).createAlarm(1, Duration.ofMillis(30000));
    verify(mailNotificationService).sendNotification("test", "raul.informatico.sound@gmail.com");
    verify(smsNotificationService, times(0)).sendNotification(any(), any());
  }

  @Test
  public void givenHealthyServiceWhenProcessAlertWithPolicyAndSMSTargetsThenSMSNotificationServiceOfFirstLevelOfEscalationIsInvokedIsInvoked() {

    // arrange
    when(policyService.getEscPolicyToSpecificService(1)).thenReturn(generateEPWithSMS());
    when(timerService.createAlarm(1, Duration.ofMillis(30000))).thenReturn(
        new Alarm.Builder().withId(1).withServiceId(1).withStarted(LocalDateTime.now()).withDuration(Duration.ofMillis(30000)).build());

    // act
    pagerService.processAlert(1, "test");

    // assert
    verify(policyService).getEscPolicyToSpecificService(1);
    verify(alertRepository)
        .createOrModifyAlert(new Alert.Builder().withServiceId(1).withLevel(1).withAlarmId(1).withMessage("test").build());
    verify(timerService).createAlarm(1, Duration.ofMillis(30000));
    verify(smsNotificationService).sendNotification("test", "654123987");
    verify(mailNotificationService, times(0)).sendNotification(any(), any());
  }

  @Test
  public void givenUnhealthyServiceWhenAlertIsReceivedThenAnyNotificationWillBeSendAndDonTSetNewAckDelay() {

    //arrange
    when(alertRepository.getAlertToService(1)).thenReturn(new Alert.Builder().withId(1).withServiceId(1).withAlarmId(2).withStarted(
        LocalDateTime.now()).build());

    // act
    pagerService.processAlert(1, "test");

    // assert
    verify(policyService, times(0)).getEscPolicyToSpecificService(any());
    verify(alertRepository, times(0)).createOrModifyAlert(any());
    verify(timerService, times(0)).createAlarm(any(), any());
    verify(smsNotificationService, times(0)).sendNotification(any(), any());
    verify(mailNotificationService, times(0)).sendNotification(any(), any());
  }

  @Test
  public void givenHealthyServiceWithoutPolicyWhenProcessAlertIsInvokedThenNotificationsAreNoSend() {
    // arrange
    when(policyService.getEscPolicyToSpecificService(1)).thenReturn(null);
    when(timerService.createAlarm(1, Duration.ofMillis(30000))).thenReturn(
        new Alarm.Builder().withId(1).withServiceId(1).withStarted(LocalDateTime.now()).withDuration(Duration.ofMillis(30000)).build());

    // act
    pagerService.processAlert(1, "test");

    // assert
    verify(policyService).getEscPolicyToSpecificService(1);
    verify(alertRepository).createOrModifyAlert(any());
    verify(timerService).createAlarm(1, Duration.ofMillis(30000));
    verify(smsNotificationService, times(0)).sendNotification(any(), any());
    verify(mailNotificationService, times(0)).sendNotification(any(), any());
  }

  @Test
  public void givenHealthyServiceWhenProcessAlertWithPolicyWithoutTargetThenAnyNotificationServiceIsInvokedIsInvoked() {
    // arrange
    when(policyService.getEscPolicyToSpecificService(1)).thenReturn(generateEPWithoutTarget());
    when(timerService.createAlarm(1, Duration.ofMillis(30000))).thenReturn(
        new Alarm.Builder().withId(1).withServiceId(1).withStarted(LocalDateTime.now()).withDuration(Duration.ofMillis(30000)).build());

    // act
    pagerService.processAlert(1, "test");

    // assert
    verify(policyService).getEscPolicyToSpecificService(1);
    verify(alertRepository).createOrModifyAlert(any());
    verify(timerService).createAlarm(1, Duration.ofMillis(30000));
    verify(smsNotificationService, times(0)).sendNotification(any(), any());
    verify(mailNotificationService, times(0)).sendNotification(any(), any());
  }

  @Test
  public void givenUnhealthyServiceWhenProcessAckThenAlarmIsRemoved() {

    // arrange
    when(alertRepository.getAlertToService(1)).thenReturn(new Alert.Builder().withId(1).withLevel(1).build());

    // act
    pagerService.processAck(1);

    // assert
    verify(alertRepository).createOrModifyAlert(new Alert.Builder().withId(1).withLevel(1).withAck(Boolean.TRUE).build());
  }

  @Test
  public void givenHealthyServiceWhenProcessAckThenDoNotHappenNothing() {

    // arrange
    when(alertRepository.getAlertToService(1)).thenReturn(null);

    // act
    pagerService.processAck(1);

    // assert
    verify(alertRepository, times(0)).createOrModifyAlert(any());
  }

  @Test
  public void givenHealthyServiceWhenProcessHealthyIsReceivedThenNoMethodIsInvoked() {

    // arrange
    when(alertRepository.getAlertToService(1)).thenReturn(null);

    // act
    pagerService.processHealthy(1);

    // assert
    verify(timerService, times(0)).removeAlarm(any());
    verify(alertRepository, times(0)).removeAlert(any());
  }

  @Test
  public void givenUnhealthyServiceWhenProcessHealthyIsReceivedThenAlarmIsRemovedAndAlertRepositoryModifiedMethodIsInvoked() {

    // arrange
    when(alertRepository.getAlertToService(1)).thenReturn(new Alert.Builder().withId(1).withLevel(1).withAlarmId(1).build());
    when(timerService.getAlarm(1)).thenReturn(
        new Alarm.Builder().withId(1).withServiceId(1).withStarted(LocalDateTime.now()).withDuration(Duration.ofMillis(30000)).build());

    // act
    pagerService.processHealthy(1);

    // assert
    verify(timerService).removeAlarm(1);
    verify(alertRepository).removeAlert(1);
  }

  @Test
  public void givenUnhealthyServiceWithoutAckTimeoutCreatedWhenProcessHealthyIsReceivedThenExceptionIsReceived() {

    // arrange
    when(alertRepository.getAlertToService(1)).thenReturn(new Alert.Builder().withId(1).withLevel(1).withAlarmId(1).build());
    when(timerService.getAlarm(1)).thenReturn(null);

    // act
    Assertions.assertThrows(AcknowledgedTimerNotFound.class, () -> {
      pagerService.processHealthy(1);
    });

  }

  @Test
  public void givenUnhealthyServiceWhenAckTimeoutIsReceivedThenLevelIsIncreasedNewTimeoutIsCreatedAndAllTargetsAreNotified() {

    // arrange
    Alert alert = new Alert.Builder().withId(1).withServiceId(1).withAlarmId(2).withStarted(
        LocalDateTime.now()).withLevel(1).withMessage("Test").build();
    when(alertRepository.getAllAlert()).thenReturn(List.of(alert));
    when(timerService.getAlarm(2)).thenReturn(new Alarm.Builder().withId(2).withServiceId(1).withStarted(LocalDateTime.now().minus(35000,
        ChronoUnit.MILLIS)).withDuration(Duration.ofMillis(30000)).build());
    when(timerService.createAlarm(1, Duration.ofMillis(30000)))
        .thenReturn(new Alarm.Builder().withId(4).withServiceId(1).withStarted(LocalDateTime.now().minus(35000,
            ChronoUnit.MILLIS)).withDuration(Duration.ofMillis(30000)).build());
    when(policyService.getEscPolicyToSpecificService(1)).thenReturn(generateEPWithSMS());

    // act
    pagerService.scheduledVerificationAlarms();

    // assert
    verify(timerService).removeAlarm(2);
    verify(alertRepository).createOrModifyAlert(
        new Alert.Builder().withId(1).withServiceId(1).withAlarmId(4).withLevel(2).withStarted(alert.getStarted()).withMessage("Test")
            .build());
    verify(smsNotificationService).sendNotification("Test", "654123989");
    verify(mailNotificationService, times(0)).sendNotification(any(), any());

  }

  @Test
  public void givenUnhealthyServiceWithACKOKReceivedWhenAckTimeoutIsReceivedThenAnyTargetIsNotifiedAndNotDelayIsCreated() {

    // arrange
    Alert alert = new Alert.Builder().withId(1).withServiceId(1).withAlarmId(2).withStarted(
        LocalDateTime.now()).withLevel(1).withMessage("Test").withAck(Boolean.TRUE).build();
    when(alertRepository.getAllAlert()).thenReturn(List.of(alert));

    // act
    pagerService.scheduledVerificationAlarms();

    // assert
    verify(timerService, times(0)).getAlarm(2);
    verify(timerService, times(0)).removeAlarm(any());
    verify(alertRepository, times(0)).createOrModifyAlert(any());
    verify(policyService, times(0)).getEscPolicyToSpecificService(any());
    verify(mailNotificationService, times(0)).sendNotification(any(), any());
    verify(smsNotificationService, times(0)).sendNotification(any(), any());

  }

  @Test
  public void givenUnhealthyServiceWithoutAcknowledgementTimeoutWhenScheduleFunctionIsExecutedThenAnyInformationAboutThatServicesIsModified() {

    // arrange
    Alert alert = new Alert.Builder().withId(1).withServiceId(1).withAlarmId(2).withStarted(
        LocalDateTime.now()).withLevel(1).withMessage("Test").build();
    when(alertRepository.getAllAlert()).thenReturn(List.of(alert));
    when(timerService.getAlarm(2)).thenReturn(new Alarm.Builder().withId(2).withServiceId(1).withStarted(LocalDateTime.now().minus(20000,
        ChronoUnit.MILLIS)).withDuration(Duration.ofMillis(30000)).build());

    // act
    pagerService.scheduledVerificationAlarms();

    // assert
    verify(timerService, times(0)).removeAlarm(any());
    verify(alertRepository, times(0)).createOrModifyAlert(any());
    verify(policyService, times(0)).getEscPolicyToSpecificService(any());
    verify(mailNotificationService, times(0)).sendNotification(any(), any());
    verify(smsNotificationService, times(0)).sendNotification(any(), any());

  }

  @Test
  public void givenUnhealthyServiceWithoutACKDelayCreatedWhenScheduleFunctionIsExecutedThenException() {

    // arrange
    Alert alert = new Alert.Builder().withId(1).withServiceId(1).withAlarmId(2).withStarted(
        LocalDateTime.now()).withLevel(1).withMessage("Test").build();
    when(alertRepository.getAllAlert()).thenReturn(List.of(alert));
    when(timerService.getAlarm(2)).thenReturn(null);

    // act
    Assertions.assertThrows(AcknowledgedTimerNotFound.class, () -> {
      pagerService.scheduledVerificationAlarms();
    });
  }

}