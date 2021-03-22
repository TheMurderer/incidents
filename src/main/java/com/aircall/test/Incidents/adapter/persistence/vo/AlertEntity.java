package com.aircall.test.Incidents.adapter.persistence.vo;

import java.time.LocalDateTime;
import java.util.Objects;

public class AlertEntity {

  private Integer id;

  private Integer serviceId;

  private Integer levelId;

  private Integer alarmId;

  private String message;

  private LocalDateTime started;

  private LocalDateTime stopped;

  private LocalDateTime ack;

  public Integer getId() {
    return id;
  }

  public Integer getServiceId() {
    return serviceId;
  }

  public String getMessage() {
    return message;
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

  public LocalDateTime getAck() {
    return ack;
  }

  public void setStopped(LocalDateTime stopped) {
    this.stopped = stopped;
  }

  public static class Builder {

    private final AlertEntity object;

    public Builder() {
      object = new AlertEntity();
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

    public Builder withAck(LocalDateTime value) {
      object.ack = value;
      return this;
    }

    public AlertEntity build() {
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
    AlertEntity alert = (AlertEntity) o;
    return Objects.equals(id, alert.id) && Objects.equals(serviceId, alert.serviceId) && Objects
        .equals(levelId, alert.levelId) && Objects.equals(alarmId, alert.alarmId) && Objects
        .equals(started, alert.started) && Objects.equals(message, alert.message) && Objects.equals(stopped, alert.stopped) && Objects
        .equals(ack, alert.ack);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, serviceId, levelId, alarmId, started, stopped, ack, message);
  }

}
