package com.aircall.test.Incidents.adapter.timer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.aircall.test.Incidents.domain.Alarm;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimerServiceAdapterTest {

  private AlarmTimerToAlarmConverter converter;

  private TimerServiceAdapter adapter;

  @BeforeEach
  void setUp() {
    converter = mock(AlarmTimerToAlarmConverter.class);
    adapter = new TimerServiceAdapter(converter);
  }

  @Test
  public void whenGetAlarmThenConverterIsInvoked() {

    //arrange
    Alarm alarm = new Alarm.Builder().withId(1).withServiceId(1).withStarted(LocalDateTime.now()).withDuration(Duration.ofHours(2)).build();
    when(converter.convert(any())).thenReturn(alarm);

    // act
    Alarm target = adapter.getAlarm(1);

    // assert
    assertNotNull(target);
    assertEquals(alarm, target);
  }
}