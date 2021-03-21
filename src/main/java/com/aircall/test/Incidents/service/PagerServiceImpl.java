package com.aircall.test.Incidents.service;

import com.aircall.test.Incidents.port.AlertRepository;
import com.aircall.test.Incidents.port.EscPolicyService;
import com.aircall.test.Incidents.port.MailNotificationService;
import com.aircall.test.Incidents.port.SMSNotificationService;
import com.aircall.test.Incidents.port.TimerService;
import com.aircall.test.Incidents.service.api.PagerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PagerServiceImpl implements PagerService {

  private Integer durationAlarm;

  private final EscPolicyService policyService;

  private final AlertRepository alertRepository;

  private final MailNotificationService mailNotificationService;

  private final SMSNotificationService smsNotificationService;

  private final TimerService timerService;

  public PagerServiceImpl(EscPolicyService policyService, AlertRepository alertRepository,
      MailNotificationService mailNotificationService, SMSNotificationService smsNotificationService,
      TimerService timerService, @Value("${incidents.alarmTimer.default.duration}") Integer durationAlarm) {
    this.policyService = policyService;
    this.alertRepository = alertRepository;
    this.mailNotificationService = mailNotificationService;
    this.smsNotificationService = smsNotificationService;
    this.timerService = timerService;
    this.durationAlarm = durationAlarm;
  }

  @Override
  public void processAlert(Integer serviceId, String message) {

  }

  @Override
  public void processAck(Integer serviceId) {

  }

  @Override
  public void processHealthy(Integer serviceId) {

  }

  @Override
  @Scheduled(fixedRate = 30000)
  public void scheduledVerificationAlarms() {

  }
}
