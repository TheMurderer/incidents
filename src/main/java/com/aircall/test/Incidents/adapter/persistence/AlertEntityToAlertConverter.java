package com.aircall.test.Incidents.adapter.persistence;

import com.aircall.test.Incidents.adapter.persistence.vo.AlertEntity;
import com.aircall.test.Incidents.domain.Alert;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AlertEntityToAlertConverter implements Converter<AlertEntity, Alert> {

  @Override
  public Alert convert(AlertEntity alertEntity) {
    return null;
  }
}
