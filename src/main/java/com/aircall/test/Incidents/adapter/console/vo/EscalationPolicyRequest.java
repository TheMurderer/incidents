package com.aircall.test.Incidents.adapter.console.vo;

import java.util.List;
import java.util.Objects;

public class EscalationPolicyRequest {

  private Integer id;

  private List<LevelRequest> levels;

  public Integer getId() {
    return id;
  }

  public List<LevelRequest> getLevels() {
    return levels;
  }

  public void addLevel(LevelRequest level) {
    levels.add(level);
  }

  public void removeLevel(Integer level) {
    levels.remove(level);
  }

  public static class Builder {

    private final EscalationPolicyRequest object;

    public Builder() {
      object = new EscalationPolicyRequest();
    }

    public Builder withId(Integer value) {
      object.id = value;
      return this;
    }

    public Builder withLevels(List value) {
      object.levels = value;
      return this;
    }

    public EscalationPolicyRequest build() {
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
    EscalationPolicyRequest that = (EscalationPolicyRequest) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
