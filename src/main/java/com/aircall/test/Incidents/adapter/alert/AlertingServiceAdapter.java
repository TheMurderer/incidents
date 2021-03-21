package com.aircall.test.Incidents.adapter.alert;

import com.aircall.test.Incidents.port.AlertingService;
import com.aircall.test.Incidents.service.api.PagerService;
import org.springframework.stereotype.Component;

@Component
public class AlertingServiceAdapter implements AlertingService {

  private final PagerService pagerService;

  public AlertingServiceAdapter(PagerService pagerService) {
    this.pagerService = pagerService;
  }

  @Override
  public void sendAlert(String message, Integer serviceId) {

  }
}
