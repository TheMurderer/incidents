package com.aircall.test.Incidents.adapter.mailNotification;

import com.aircall.test.Incidents.port.MailNotificationService;
import org.springframework.stereotype.Component;

@Component
public class MailNotificationServiceAdapter implements MailNotificationService {

  @Override
  public void setNotification(String message, String emailAddress) {

  }
}
