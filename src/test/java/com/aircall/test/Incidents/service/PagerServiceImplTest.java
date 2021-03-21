package com.aircall.test.Incidents.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.aircall.test.Incidents.domain.Alert;
import com.aircall.test.Incidents.domain.EscalationPolicy;
import com.aircall.test.Incidents.domain.Target;
import com.aircall.test.Incidents.domain.TargetType;
import com.aircall.test.Incidents.port.AlertRepository;
import com.aircall.test.Incidents.port.EscPolicyService;
import com.aircall.test.Incidents.port.MailNotificationService;
import com.aircall.test.Incidents.port.SMSNotificationService;
import com.aircall.test.Incidents.port.TimerService;
import com.aircall.test.Incidents.service.api.PagerService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
    HashMap<Integer, List<Target>> levels = new HashMap<>();
    levels.put(1, List.of(target));
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
        new PagerServiceImpl(policyService, alertRepository, mailNotificationService, smsNotificationService, timerService, 30000);

  }

  @Test
  public void whenProcessAlertThenMailServiceIsInvokedIsInvoked() {
    // arrange
    when(policyService.getEscPolicyToSpecificService(1)).thenReturn(generateEPWithEmail());

    // act
    pagerService.processAlert(1, "test");

    // assert
    verify(policyService).getEscPolicyToSpecificService(1);
    verify(alertRepository).createAlert(any());
    verify(timerService).setAlarm(1, Duration.ofMillis(30000));
    verify(mailNotificationService).setNotification("test", "raul.informatico.sound@gmail.com");
    verify(smsNotificationService, times(0)).setNotification(any(), any());
  }

  @Test
  public void whenProcessAlertThenSMSServiceIsInvokedIsInvoked() {
    // arrange
    when(policyService.getEscPolicyToSpecificService(1)).thenReturn(generateEPWithSMS());

    // act
    pagerService.processAlert(1, "test");

    // assert
    verify(policyService).getEscPolicyToSpecificService(1);
    verify(alertRepository).createAlert(any());
    verify(timerService).setAlarm(1, Duration.ofMillis(30000));
    verify(smsNotificationService).setNotification("test", "654123987");
    verify(mailNotificationService, times(0)).setNotification(any(), any());
  }

  @Test
  public void whenProcessAlertWithPolicyWithoutTargetThenAnyNotificationServiceIsInvokedIsInvoked() {
    // arrange
    when(policyService.getEscPolicyToSpecificService(1)).thenReturn(generateEPWithoutTarget());

    // act
    pagerService.processAlert(1, "test");

    // assert
    verify(policyService).getEscPolicyToSpecificService(1);
    verify(alertRepository).createAlert(any());
    verify(timerService).setAlarm(1, Duration.ofMillis(30000));
    verify(smsNotificationService, times(0)).setNotification(any(), any());
    verify(mailNotificationService, times(0)).setNotification(any(), any());
  }

  @Test
  public void whenProcessAckThenAlarmIsRemoved() {

    // act
    pagerService.processAck(1);

    // assert
    verify(timerService).removeAlarm(1);
  }

  @Test
  public void whenProcessHealthyThenAlarmIsRemovedAndAlertRepositoryModifiedMethodInvoked() {

    // act
    pagerService.processHealthy(1);

    // assert
    verify(timerService).removeAlarm(1);
    verify(alertRepository).removeAlert(1);
  }

  @Test
  public void whenScheduledVerificationAlarmsThenGetAlarmAndRepositoryMethodIsInvoked() {

    when(alertRepository.getAlertToService(1)).thenReturn(new Alert.Builder().withId(1).withServiceId(1).withAlarmId(2).withStarted(
        LocalDateTime.now()).build());
    // act
    pagerService.scheduledVerificationAlarms();

    // assert
    verify(alertRepository).getAlertToService(1);
    verify(timerService).getAlarm(2);
  }
}