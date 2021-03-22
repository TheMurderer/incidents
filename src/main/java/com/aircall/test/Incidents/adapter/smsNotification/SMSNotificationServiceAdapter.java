package com.aircall.test.Incidents.adapter.smsNotification;

import com.aircall.test.Incidents.port.SMSNotificationService;
import org.springframework.stereotype.Component;

@Component
public class SMSNotificationServiceAdapter implements SMSNotificationService {

  @Override
  public void sendNotification(String message, String emailAddress) {

  }
}
