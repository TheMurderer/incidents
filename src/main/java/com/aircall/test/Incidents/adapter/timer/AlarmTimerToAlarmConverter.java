package com.aircall.test.Incidents.adapter.timer;

import com.aircall.test.Incidents.adapter.timer.vo.AlarmTimer;
import com.aircall.test.Incidents.domain.Alarm;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AlarmTimerToAlarmConverter implements Converter<AlarmTimer, Alarm> {

  @Override
  public Alarm convert(AlarmTimer alarmTimer) {
    return null;
  }
}
