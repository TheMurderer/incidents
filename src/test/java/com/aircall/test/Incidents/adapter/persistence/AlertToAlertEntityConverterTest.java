package com.aircall.test.Incidents.adapter.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.aircall.test.Incidents.adapter.persistence.vo.AlertEntity;
import com.aircall.test.Incidents.domain.Alert;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlertToAlertEntityConverterTest {

  private AlertToAlertEntityConverter converter;

  @BeforeEach
  void setUp() {
    converter = new AlertToAlertEntityConverter();
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
    AlertEntity target = converter.convert(null);

    // assert
    assertNull(target);
  }

  @Test
  public void givenAAlertWhenConvertIsInvokedThenAlertEntityIsReceived() {

    // act
    AlertEntity target = converter.convert(generateAlert());

    // assert
    assertNotNull(target);
    assertEquals(generateAlertEntity(), target);
  }
}