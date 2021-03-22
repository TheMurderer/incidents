package com.aircall.test.Incidents.adapter.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.aircall.test.Incidents.domain.Alert;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class AlertRepositoryAdapterTest {

  private AlertEntityToAlertConverter entityToAlertConverter;

  private AlertToAlertEntityConverter alertToAlertEntityConverter;

  private AlertRepositoryAdapter adapter;

  @BeforeEach
  void setUp() {
    entityToAlertConverter = mock(AlertEntityToAlertConverter.class);
    alertToAlertEntityConverter = mock(AlertToAlertEntityConverter.class);

    adapter = new AlertRepositoryAdapter(entityToAlertConverter, alertToAlertEntityConverter);
  }

  private Alert generateAlert() {
    return new Alert.Builder().withId(1).withAlarmId(1).withLevel(1).withStarted(LocalDateTime.now()).build();
  }

  @Test
  public void whenGetAlertToServiceThenEntityToAlertConverterIsInvoked() {
    //arrange
    Alert alert = generateAlert();
    when(entityToAlertConverter.convert(any())).thenReturn(alert);

    // act
    Alert target = adapter.getAlertToService(1);

    // assert
    verify(entityToAlertConverter).convert(any());
    assertNotNull(target);
    assertEquals(alert,target);
  }

  @Test
  public void whenCreateAlertThenAlertToAlertEntityConverterIsInvoked() {

    // act
    adapter.createOrModifyAlert(generateAlert());

    // assert
    verify(entityToAlertConverter).convert(any());
  }

}