package com.aircall.test.Incidents.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Alert {

  private Integer id;

  private Integer serviceId;

  private Integer levelId;

  private Integer alarmId;

  private LocalDateTime started;

  private LocalDateTime stopped;

  public Integer getId() {
    return id;
  }

  public Integer getServiceId() {
    return serviceId;
  }

  public Integer getLevelId() {
    return levelId;
  }

  public Integer getAlarmId() {
    return alarmId;
  }

  public LocalDateTime getStarted() {
    return started;
  }

  public LocalDateTime getStopped() {
    return stopped;
  }

  public void setStopped(LocalDateTime stopped) {
    this.stopped = stopped;
  }

  public static class Builder {

    private final Alert object;

    public Builder() {
      object = new Alert();
    }

    public Builder withId(Integer value) {
      object.id = value;
      return this;
    }

    public Builder withServiceId(Integer value) {
      object.serviceId = value;
      return this;
    }

    public Builder withLevelId(Integer value) {
      object.levelId = value;
      return this;
    }

    public Builder withAlarmId(Integer value) {
      object.alarmId = value;
      return this;
    }

    public Builder withStarted(LocalDateTime value) {
      object.started = value;
      return this;
    }

    public Builder withStopped(LocalDateTime value) {
      object.stopped = value;
      return this;
    }

    public Alert build() {
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
    Alert alert = (Alert) o;
    return Objects.equals(id, alert.id) && Objects.equals(serviceId, alert.serviceId) && Objects
        .equals(levelId, alert.levelId) && Objects.equals(alarmId, alert.alarmId) && Objects
        .equals(started, alert.started) && Objects.equals(stopped, alert.stopped);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, serviceId, levelId, alarmId, started, stopped);
  }
}
