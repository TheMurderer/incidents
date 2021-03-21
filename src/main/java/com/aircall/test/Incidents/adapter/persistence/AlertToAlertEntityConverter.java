package com.aircall.test.Incidents.adapter.persistence;

import com.aircall.test.Incidents.adapter.persistence.vo.AlertEntity;
import com.aircall.test.Incidents.domain.Alert;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AlertToAlertEntityConverter implements Converter<Alert, AlertEntity> {

  @Override
  public AlertEntity convert(Alert alert) {
    return null;
  }
}
