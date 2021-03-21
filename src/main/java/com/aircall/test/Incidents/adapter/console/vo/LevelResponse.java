package com.aircall.test.Incidents.adapter.console.vo;

import java.util.List;
import java.util.Objects;

public class LevelResponse {

  private Integer id;

  private List<TargetResponse> targets;

  public Integer getId() {
    return id;
  }

  public List<TargetResponse> getTargets() {
    return targets;
  }

  public static class Builder {

    private final LevelResponse object;

    public Builder() {
      object = new LevelResponse();
    }

    public Builder withId(Integer value) {
      object.id = value;
      return this;
    }

    public Builder withTarget(List value) {
      object.targets = value;
      return this;
    }

    public LevelResponse build() {
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
    LevelResponse that = (LevelResponse) o;
    return Objects.equals(id, that.id) && Objects.equals(targets, that.targets);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, targets);
  }
}
