package com.aircall.test.Incidents.port;

import com.aircall.test.Incidents.domain.Alert;
import java.util.List;

public interface AlertRepository {

  List<Alert> getAllAlert();

  Alert getAlertToService(Integer serviceId);

  void createOrModifyAlert(Alert alert);

  void removeAlert(Integer alertId);
}
