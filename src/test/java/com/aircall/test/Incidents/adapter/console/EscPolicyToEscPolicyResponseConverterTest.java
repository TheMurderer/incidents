package com.aircall.test.Incidents.adapter.console;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.aircall.test.Incidents.adapter.console.vo.EscalationPolicyResponse;
import com.aircall.test.Incidents.adapter.console.vo.LevelResponse;
import com.aircall.test.Incidents.adapter.console.vo.TargetResponse;
import com.aircall.test.Incidents.domain.EscalationPolicy;
import com.aircall.test.Incidents.domain.Target;
import com.aircall.test.Incidents.domain.TargetType;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EscPolicyToEscPolicyResponseConverterTest {

  private EscPolicyToEscPolicyResponseConverter converter;

  @BeforeEach
  void setUp() {
    converter = new EscPolicyToEscPolicyResponseConverter();
  }

  private EscalationPolicyResponse generateEPResponse() {
    TargetResponse
        target =
        new TargetResponse.Builder().withId(1).withType(TargetType.EMAIL).withReference("raul.informatico.sound@gmail.com").build();

    LevelResponse level = new LevelResponse.Builder().withId(1).withTarget(List.of(target)).build();
    return new EscalationPolicyResponse.Builder().withId(1).withLevels(List.of(level)).build();
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
    EscalationPolicyResponse target = converter.convert(null);

    // assert
    assertNull(target);
  }

  @Test
  public void givenAValidEscPolicyWhenConvertIsInvokedThenCorrectEscPolicyResponseIsReceived() {

    // arrange
    EscalationPolicy ep = generateEP();

    // act
    EscalationPolicyResponse target = converter.convert(ep);

    // assert
    assertNotNull(target);
    assertEquals(generateEPResponse(), target);
  }
}