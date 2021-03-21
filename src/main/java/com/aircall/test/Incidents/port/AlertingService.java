package com.aircall.test.Incidents.port;

public interface AlertingService {

  void sendAlert(String message, Integer serviceId);

}
