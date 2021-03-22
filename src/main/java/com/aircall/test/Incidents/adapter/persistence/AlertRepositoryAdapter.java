package com.aircall.test.Incidents.adapter.persistence;

import com.aircall.test.Incidents.domain.Alert;
import com.aircall.test.Incidents.port.AlertRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AlertRepositoryAdapter implements AlertRepository {

  private final AlertEntityToAlertConverter entityToAlertConverter;

  private final AlertToAlertEntityConverter alertToAlertEntityConverter;

  public AlertRepositoryAdapter(AlertEntityToAlertConverter entityToAlertConverter,
      AlertToAlertEntityConverter alertToAlertEntityConverter) {
    this.entityToAlertConverter = entityToAlertConverter;
    this.alertToAlertEntityConverter = alertToAlertEntityConverter;
  }

  @Override
  public List<Alert> getAllAlert() {
    return new ArrayList<>();
  }

  @Override
  public Alert getAlertToService(Integer serviceId) {
    return null;
  }

  @Override
  public void createOrModifyAlert(Alert alert) {

  }

  @Override
  public void removeAlert(Integer alertId) {

  }
}
