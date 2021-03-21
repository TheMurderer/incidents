package com.aircall.test.Incidents.port;

import com.aircall.test.Incidents.domain.EscalationPolicy;

public interface EscPolicyService {

  EscalationPolicy getEscPolicy(Integer policyId);

  EscalationPolicy getEscPolicyToSpecificService(Integer serviceId);

  void modifyEscPolicy(EscalationPolicy escPolicy);
}
