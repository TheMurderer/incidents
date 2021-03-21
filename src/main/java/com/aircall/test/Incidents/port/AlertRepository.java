package com.aircall.test.Incidents.port;

import com.aircall.test.Incidents.domain.Alert;

public interface AlertRepository {

  Alert getAlertToService(Integer serviceId);

  void createAlert(Alert alert);

  void removeAlert(Integer alertId);
}
