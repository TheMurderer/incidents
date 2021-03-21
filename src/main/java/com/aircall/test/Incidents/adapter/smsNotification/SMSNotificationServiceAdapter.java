package com.aircall.test.Incidents.adapter.smsNotification;

import com.aircall.test.Incidents.port.SMSNotificationService;
import org.springframework.stereotype.Component;

@Component
public class SMSNotificationServiceAdapter implements SMSNotificationService {

  @Override
  public void setNotification(String message, String emailAddress) {

  }
}
