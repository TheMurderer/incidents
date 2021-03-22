package com.aircall.test.Incidents.port;

public interface SMSNotificationService {

  void sendNotification(String message, String emailAddress);
}
