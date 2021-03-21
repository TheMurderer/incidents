package com.aircall.test.Incidents.adapter.escpolicy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.aircall.test.Incidents.adapter.escpolicy.vo.EscPolicy;
import com.aircall.test.Incidents.adapter.escpolicy.vo.Target;
import com.aircall.test.Incidents.domain.EscalationPolicy;
import com.aircall.test.Incidents.domain.TargetType;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EscPolicyToEscalationPolicyConverterTest {

  private EscPolicyToEscalationPolicyConverter converter;

  @BeforeEach
  void setUp() {
    converter = new EscPolicyToEscalationPolicyConverter();
  }

  private EscPolicy generateEscPolicy() {
    Target target = new Target.Builder().withId(1).withType(TargetType.EMAIL).withReference("raul.informatico.sound@gmail.com").build();
    HashMap<Integer, List<Target>> levels = new HashMap<>();
    levels.put(1, List.of(target));
    return new EscPolicy.Builder().withId(1).withLevels(levels).build();
  }

  private EscalationPolicy generateEP() {
    Target target = new Target.Builder().withId(1).withType(TargetType.EMAIL).withReference("raul.informatico.sound@gmail.com").build();
    HashMap<Integer, List<Target>> levels = new HashMap<>();
    levels.put(1, List.of(target));
    return new EscalationPolicy.Builder().withId(1).withLevels(levels).build();
  }

  @Test
  public void givenNullValueWhenConvertIsInvokedThenNullIsReceived() {

    // act
    EscalationPolicy target = converter.convert(null);

    // assert
    assertNull(target);
  }

  @Test
  public void givenAValidEscPolicyWhenConvertIsInvokedThenCorrectEscalatioPolicyIsReceived() {

    // act
    EscalationPolicy target = converter.convert(generateEscPolicy());

    // assert
    assertNotNull(target);
    assertEquals(generateEP(), target);
  }
}