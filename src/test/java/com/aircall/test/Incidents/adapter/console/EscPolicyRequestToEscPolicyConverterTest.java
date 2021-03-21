package com.aircall.test.Incidents.adapter.console;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.aircall.test.Incidents.adapter.console.vo.EscalationPolicyRequest;
import com.aircall.test.Incidents.adapter.console.vo.LevelRequest;
import com.aircall.test.Incidents.adapter.console.vo.TargetRequest;
import com.aircall.test.Incidents.domain.EscalationPolicy;
import com.aircall.test.Incidents.domain.Target;
import com.aircall.test.Incidents.domain.TargetType;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EscPolicyRequestToEscPolicyConverterTest {

  private EscPolicyRequestToEscPolicyConverter converter;

  @BeforeEach
  void setUp() {
    converter = new EscPolicyRequestToEscPolicyConverter();
  }

  private EscalationPolicyRequest generateEPRequest() {
    TargetRequest
        target = new TargetRequest.Builder().withId(1).withType(TargetType.EMAIL).withReference("raul.informatico.sound@gmail.com").build();

    LevelRequest level = new LevelRequest.Builder().withId(1).withTarget(List.of(target)).build();
    return new EscalationPolicyRequest.Builder().withId(1).withLevels(List.of(level)).build();
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
  public void givenAValidEscPolicyRequestWhenConvertIsInvokedThenCorrectEscPolicyIsReceived() {

    // arrange
    EscalationPolicyRequest epRequest = generateEPRequest();

    // act
    EscalationPolicy target = converter.convert(epRequest);

    // assert
    assertNotNull(target);
    assertEquals(generateEP(), target);
  }
}