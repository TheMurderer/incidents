package com.aircall.test.Incidents.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Alert {

  private Integer id;

  private Integer serviceId;

  private Integer level;

  private Integer alarmId;

  private String message;

  private LocalDateTime started;

  private LocalDateTime stopped;

  private Boolean ack;

  public Integer getServiceId() {
    return serviceId;
  }

  public Integer getLevel() {
    return level;
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

  public Boolean getAck() {
    return ack;
  }

  public Integer getId() {
    return id;
  }

  public String getMessage() {
    return message;
  }

  public void setAlarmId(Integer alarmId) {
    this.alarmId = alarmId;
  }

  public void setAck(Boolean ack) {
    this.ack = ack;
  }

  public void setLevel(Integer level) {
    this.level = level;
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

    public Builder withLevel(Integer value) {
      object.level = value;
      return this;
    }

    public Builder withAlarmId(Integer value) {
      object.alarmId = value;
      return this;
    }

    public Builder withMessage(String value) {
      object.message = value;
      return this;
    }

    public Builder withStarted(LocalDateTime value) {
      object.started = value;
      return this;
    }

    public Builder withAck(Boolean value) {
      object.ack = value;
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
    return Objects.equals(serviceId, alert.serviceId) && Objects
        .equals(level, alert.level) && Objects.equals(alarmId, alert.alarmId) && Objects.equals(ack, alert.ack);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serviceId, level, alarmId, started, stopped, ack);
  }
}
