package com.aircall.test.Incidents.adapter.console.vo;

import com.aircall.test.Incidents.domain.TargetType;
import java.util.Objects;

public class TargetRequest {

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

    private final TargetRequest object;

    public Builder() {
      object = new TargetRequest();
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

    public TargetRequest build() {
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
    TargetRequest that = (TargetRequest) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
