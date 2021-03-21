package com.aircall.test.Incidents.adapter.console.vo;

import java.util.List;
import java.util.Objects;

public class EscalationPolicyResponse {

  private Integer id;

  private List<LevelResponse> levels;

  public Integer getId() {
    return id;
  }

  public List<LevelResponse> getLevels() {
    return levels;
  }

  public void addLevel(LevelResponse level) {
    levels.add(level);
  }

  public void removeLevel(Integer level) {
    levels.remove(level);
  }

  public static class Builder {

    private final EscalationPolicyResponse object;

    public Builder() {
      object = new EscalationPolicyResponse();
    }

    public Builder withId(Integer value) {
      object.id = value;
      return this;
    }

    public Builder withLevels(List value) {
      object.levels = value;
      return this;
    }

    public EscalationPolicyResponse build() {
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
    EscalationPolicyResponse that = (EscalationPolicyResponse) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
