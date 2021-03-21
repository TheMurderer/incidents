package com.aircall.test.Incidents.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Alarm {

  private Integer id;

  private Integer serviceId;

  private LocalDateTime started;

  private Duration duration;

  public Integer getId() {
    return id;
  }

  public Integer getServiceId() {
    return serviceId;
  }

  public LocalDateTime getStarted() {
    return started;
  }

  public Duration getDuration() {
    return duration;
  }

  public static class Builder {

    private final Alarm object;

    public Builder() {
      object = new Alarm();
    }

    public Builder withId(Integer value) {
      object.id = value;
      return this;
    }

    public Builder withServiceId(Integer value) {
      object.serviceId = value;
      return this;
    }

    public Builder withStarted(LocalDateTime value) {
      object.started = value;
      return this;
    }

    public Builder withDuration(Duration value) {
      object.duration = value;
      return this;
    }

    public Alarm build() {
      return object;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Alarm alarmTimer = (Alarm) o;
    return Objects.equals(id, alarmTimer.id) && Objects.equals(serviceId, alarmTimer.serviceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, serviceId);
  }
}
