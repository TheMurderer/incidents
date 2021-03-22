package com.aircall.test.Incidents.port;

public interface MailNotificationService {

  void sendNotification(String message, String emailAddress);

}
