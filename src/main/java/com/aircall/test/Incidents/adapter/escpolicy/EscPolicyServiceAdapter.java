package com.aircall.test.Incidents.adapter.escpolicy;

import com.aircall.test.Incidents.domain.EscalationPolicy;
import com.aircall.test.Incidents.port.EscPolicyService;
import org.springframework.stereotype.Component;

@Component
public class EscPolicyServiceAdapter implements EscPolicyService {

  private final EscalationPolicyToEscPolicyConverter policyToEscPolicyConverter;
  private final EscPolicyToEscalationPolicyConverter EscPolicyToPolicyConverter;

  public EscPolicyServiceAdapter(EscalationPolicyToEscPolicyConverter policyToEscPolicyConverter,
      EscPolicyToEscalationPolicyConverter escPolicyToPolicyConverter) {
    this.policyToEscPolicyConverter = policyToEscPolicyConverter;
    EscPolicyToPolicyConverter = escPolicyToPolicyConverter;
  }

  @Override
  public EscalationPolicy getEscPolicy(Integer policyId) {
    return null;
  }

  @Override
  public EscalationPolicy getEscPolicyToSpecificService(Integer serviceId) {
    return null;
  }

  @Override
  public void modifyEscPolicy(EscalationPolicy escPolicy) {

  }

}
