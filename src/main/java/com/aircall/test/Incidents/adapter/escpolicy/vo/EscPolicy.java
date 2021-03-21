package com.aircall.test.Incidents.adapter.escpolicy.vo;

import java.util.HashMap;
import java.util.List;

public class EscPolicy {
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

    private final EscPolicy object;

    public Builder() {
      object = new EscPolicy();
    }

    public Builder withId(Integer value) {
      object.id = value;
      return this;
    }

    public Builder withLevels(HashMap value) {
      object.levels = value;
      return this;
    }

    public EscPolicy build() {
      return object;
    }

  }
}
