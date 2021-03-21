package com.aircall.test.Incidents.adapter.alert;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.aircall.test.Incidents.service.api.PagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlertingServiceAdapterTest {

  private AlertingServiceAdapter adapter;

  private PagerService pagerService;

  @BeforeEach
  void setUp() {
    pagerService = mock(PagerService.class);
    adapter = new AlertingServiceAdapter(pagerService);
  }

  @Test
  public void whenSendNotificationThenPagerIsInvoked() {

    // act
    adapter.sendAlert("exampleMessage", 1);

    // assert
    verify(pagerService).processAlert(1, "exampleMessage");
  }
}