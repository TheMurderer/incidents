package com.aircall.test.Incidents.adapter.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.aircall.test.Incidents.adapter.persistence.vo.AlertEntity;
import com.aircall.test.Incidents.domain.Alert;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlertEntityToAlertConverterTest {

  private AlertEntityToAlertConverter converter;

  @BeforeEach
  void setUp() {
    converter = new AlertEntityToAlertConverter();
  }

  private Alert generateAlert() {
    return new Alert.Builder().withId(1).withAlarmId(1).withLevelId(1).withStarted(LocalDateTime.now()).build();
  }

  private AlertEntity generateAlertEntity() {
    return new AlertEntity.Builder().withId(1).withAlarmId(1).withLevelId(1).withStarted(LocalDateTime.now()).build();
  }

  @Test
  public void givenNullValueWhenConvertIsInvokedThenNullIsReceived() {

    // act
    Alert target = converter.convert(null);

    // assert
    assertNull(target);
  }

  @Test
  public void givenAAlertEntityWhenConvertIsInvokedThenAlertIsReceived() {

    // act
    Alert target = converter.convert(generateAlertEntity());

    // assert
    assertNotNull(target);
    assertEquals(generateAlert(), target);
  }
}