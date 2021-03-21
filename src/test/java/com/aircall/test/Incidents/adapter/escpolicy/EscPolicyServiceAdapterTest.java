package com.aircall.test.Incidents.adapter.escpolicy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.aircall.test.Incidents.domain.EscalationPolicy;
import com.aircall.test.Incidents.domain.Target;
import com.aircall.test.Incidents.domain.TargetType;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EscPolicyServiceAdapterTest {

  private EscalationPolicyToEscPolicyConverter policyToEscPolicyConverter;

  private EscPolicyToEscalationPolicyConverter escPolicyToPolicyConverter;

  private EscPolicyServiceAdapter adapter;

  @BeforeEach
  void setUp() {
    policyToEscPolicyConverter = mock(EscalationPolicyToEscPolicyConverter.class);
    escPolicyToPolicyConverter = mock(EscPolicyToEscalationPolicyConverter.class);
    adapter = new EscPolicyServiceAdapter(policyToEscPolicyConverter, escPolicyToPolicyConverter);
  }

  private EscalationPolicy generateEP() {
    Target target = new Target.Builder().withId(1).withType(TargetType.EMAIL).withReference("raul.informatico.sound@gmail.com").build();
    HashMap<Integer, List<Target>> levels = new HashMap<>();
    levels.put(1, List.of(target));
    return new EscalationPolicy.Builder().withId(1).withLevels(levels).build();
  }

  @Test
  public void whenGetEscPolicyThenEscPolicyToEscalationPolicyConverterIsInvoked() {

    // act
    adapter.getEscPolicy(1);

    // assert
    verify(escPolicyToPolicyConverter).convert(any());
  }

  @Test
  public void whenGetEscPolicyToSpecificServiceThenEscPolicyToEscalationPolicyConverterIsInvoked() {

    // act
    adapter.getEscPolicyToSpecificService(1);

    // assert
    verify(escPolicyToPolicyConverter).convert(any());
  }

  @Test
  public void whenModifyEscPolicyThenEscalationPolicyToEscPolicyConverterIsInvoked() {

    // act
    adapter.modifyEscPolicy(generateEP());

    // assert
    verify(policyToEscPolicyConverter).convert(any());
  }
}