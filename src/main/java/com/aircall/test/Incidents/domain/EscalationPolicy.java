package com.aircall.test.Incidents.domain;

import java.util.HashMap;
import java.util.List;

public class EscalationPolicy {
  private Integer id;
  private HashMap<Integer, List<Target>> levels;

  public Integer getId() {
    return id;
  }

  public HashMap<Integer, List<Target>> getLevels() {
    return levels;
  }

  public void putLevel(Integer level, List<Target> targets) {
    levels.put(level, targets);
  }

  public void removeLevel(Integer level) {
    levels.remove(level);
  }

  public static class Builder {

    private final EscalationPolicy object;

    public Builder() {
      object = new EscalationPolicy();
    }

    public Builder withId(Integer value) {
      object.id = value;
      return this;
    }

    public Builder withLevels(HashMap value) {
      object.levels = value;
      return this;
    }

    public EscalationPolicy build() {
      return object;
    }

  }
}
