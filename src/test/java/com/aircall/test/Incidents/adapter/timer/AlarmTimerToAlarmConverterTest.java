package com.aircall.test.Incidents.adapter.timer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.aircall.test.Incidents.adapter.timer.vo.AlarmTimer;
import com.aircall.test.Incidents.domain.Alarm;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlarmTimerToAlarmConverterTest {

  private AlarmTimerToAlarmConverter converter;

  @BeforeEach
  void setUp() {
    converter = new AlarmTimerToAlarmConverter();
  }

  private Alarm generateAlarm() {
    return new Alarm.Builder().withId(1).withServiceId(1).withStarted(LocalDateTime.now()).withDuration(Duration.ofHours(2)).build();
  }

  private AlarmTimer generateAlarmTimer() {
    return new AlarmTimer.Builder().withId(1).withServiceId(1).withStarted(LocalDateTime.now()).withDuration(Duration.ofHours(2)).build();
  }

  @Test
  public void givenNullValueWhenConvertIsInvokedThenNullIsReceived() {

    // act
    Alarm target = converter.convert(null);

    // assert
    assertNull(target);
  }

  @Test
  public void givenAAlarmTimerWhenConvertIsInvokedThenAlarmIsReceived() {

    // act
    Alarm target = converter.convert(generateAlarmTimer());

    // assert
    assertNotNull(target);
    assertEquals(generateAlarm(), target);
  }
}