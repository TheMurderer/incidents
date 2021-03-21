package com.aircall.test.Incidents.service.api;

public interface PagerService {

  void processAlert(Integer serviceId, String message);

  void processAck(Integer serviceId);

  void processHealthy(Integer serviceId);

  void scheduledVerificationAlarms();
}
