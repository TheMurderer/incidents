package com.aircall.test.Incidents.port;

import com.aircall.test.Incidents.domain.Alarm;
import java.time.Duration;

public interface TimerService {

  Alarm createAlarm(Integer serviceId, Duration duration);

  Alarm getAlarm(Integer alarmId);

  void removeAlarm(Integer alarmId);
}
