package com.aircall.test.Incidents.service;

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
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PagerServiceImpl implements PagerService {

  private Integer initialLevel;

  private Integer durationAlarm;

  private String alarmNotFoundMessage;

  private final EscPolicyService policyService;

  private final AlertRepository alertRepository;

  private final MailNotificationService mailNotificationService;

  private final SMSNotificationService smsNotificationService;

  private final TimerService timerService;

  private final ReentrantReadWriteLock rwLock;

  public PagerServiceImpl(EscPolicyService policyService, AlertRepository alertRepository,
      MailNotificationService mailNotificationService, SMSNotificationService smsNotificationService,
      TimerService timerService, @Value("${incidents.alarmTimer.default.duration}") Integer durationAlarm,
      @Value("${incidents.alert.level.initial}") Integer initialLevel,
      @Value("${exception.ack.timer.not.found.message}") String alarmNotFoundMessage) {
    this.policyService = policyService;
    this.alertRepository = alertRepository;
    this.mailNotificationService = mailNotificationService;
    this.smsNotificationService = smsNotificationService;
    this.timerService = timerService;
    this.durationAlarm = durationAlarm;
    this.initialLevel = initialLevel;
    this.alarmNotFoundMessage = alarmNotFoundMessage;
    this.rwLock = new ReentrantReadWriteLock();
    ;
  }

  private void sendNotification(Target target, String message) {
    if (TargetType.EMAIL.equals(target.getType())) {
      mailNotificationService.sendNotification(message, target.getReference());
    } else if (TargetType.SMS.equals(target.getType())) {
      smsNotificationService.sendNotification(message, target.getReference());
    }
  }

  private void notifyTargets(Integer serviceId, String message, Integer level) {
    rwLock.readLock().lock();
    try {
      EscalationPolicy escalationPolicy = policyService.getEscPolicyToSpecificService(serviceId);
      if (escalationPolicy != null) {
        escalationPolicy.getLevels().get(level).forEach(t -> sendNotification(t, message));
      }
    } finally {
      rwLock.readLock().unlock();
    }

  }

  private Alarm setAcknowledgementDelay(Integer alarmId, Integer serviceId) {
    timerService.removeAlarm(alarmId);
    return timerService.createAlarm(serviceId, Duration.ofMillis(durationAlarm));
  }

  @Override
  @Async
  public void processAlert(Integer serviceId, String message) {
    final Alert alert = alertRepository.getAlertToService(serviceId);

    // Verify if alert is new
    if (alert == null && serviceId != null) {
      notifyTargets(serviceId, message, this.initialLevel);

      Alarm alarm = timerService.createAlarm(serviceId, Duration.ofMillis(durationAlarm));
      Alert newAlert =
          new Alert.Builder().withServiceId(serviceId).withLevel(this.initialLevel).withAlarmId(alarm.getId()).withMessage(message)
              .withStarted(LocalDateTime.now()).build();

      rwLock.writeLock().lock();
      try {
        alertRepository.createOrModifyAlert(newAlert);
      } finally {
        rwLock.writeLock().unlock();
      }
    }
  }

  @Override
  public void processAck(Integer serviceId) {
    if (serviceId != null) {
      Alert alert = alertRepository.getAlertToService(serviceId);
      if (alert != null) {
        alert.setAck(Boolean.TRUE);
        rwLock.writeLock().lock();
        try {
          alertRepository.createOrModifyAlert(alert);
        } finally {
          rwLock.writeLock().unlock();
        }
      }
    }
  }

  @Override
  public void processHealthy(Integer serviceId) {
    if (serviceId != null) {
      Alert alert = alertRepository.getAlertToService(serviceId);
      if (alert != null) {
        rwLock.writeLock().lock();
        try {
          alertRepository.removeAlert(alert.getId());
          Alarm alarm = timerService.getAlarm(alert.getAlarmId());
          if (alarm != null) {
            timerService.removeAlarm(alarm.getId());
          } else {
            throw new AcknowledgedTimerNotFound(alarmNotFoundMessage);
          }
        } finally {
          rwLock.writeLock().unlock();
        }
      }
    }
  }

  @Override
  @Scheduled(fixedRate = 30000)
  public void scheduledVerificationAlarms() {
    List<Alert> alerts = alertRepository.getAllAlert();

    alerts.stream().filter(alert -> alert.getAck() == null || Boolean.FALSE.equals(alert.getAck())).forEach(alert -> {
      Alarm alarm = timerService.getAlarm(alert.getAlarmId());

      if (alarm == null) {
        throw new AcknowledgedTimerNotFound(alarmNotFoundMessage);
      } else if (Duration.between(alarm.getStarted(), LocalDateTime.now()).compareTo(alarm.getDuration()) > 0) {
        rwLock.writeLock().lock();

        final Integer newLevel = alert.getLevel() + 1;
        try {
          Alarm acknowledgementDelayUpdated = setAcknowledgementDelay(alarm.getId(), alert.getServiceId());
          alert.setAlarmId(acknowledgementDelayUpdated.getId());
          alert.setLevel(newLevel);
          alertRepository.createOrModifyAlert(alert);
        } finally {
          rwLock.writeLock().unlock();
        }

        notifyTargets(alert.getServiceId(), alert.getMessage(), newLevel);
      }
    });
  }
}
