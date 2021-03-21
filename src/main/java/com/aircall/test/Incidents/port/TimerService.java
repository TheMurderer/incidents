package com.aircall.test.Incidents.port;

import com.aircall.test.Incidents.domain.Alarm;
import java.time.Duration;

public interface TimerService {

  void setAlarm(Integer serviceId, Duration duration);

  Alarm getAlarm(Integer serviceId);

  void removeAlarm(Integer serviceId);
}
