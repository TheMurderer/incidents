package com.aircall.test.Incidents.adapter.escpolicy.vo;

import com.aircall.test.Incidents.domain.TargetType;

public class Target {

  private Integer id;

  private TargetType type;

  private String reference;

  public Integer getId() {
    return id;
  }

  public TargetType getType() {
    return type;
  }

  public String getReference() {
    return reference;
  }

  public static class Builder {

    private final Target object;

    public Builder() {
      object = new Target();
    }

    public Builder withId(Integer value) {
      object.id = value;
      return this;
    }

    public Builder withType(TargetType value) {
      object.type = value;
      return this;
    }

    public Builder withReference(String value) {
      object.reference = value;
      return this;
    }

    public Target build() {
      return object;
    }

  }
}
