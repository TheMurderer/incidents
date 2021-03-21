package com.aircall.test.Incidents.adapter.console;

import com.aircall.test.Incidents.adapter.console.vo.EscalationPolicyRequest;
import com.aircall.test.Incidents.domain.EscalationPolicy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EscPolicyRequestToEscPolicyConverter implements Converter<EscalationPolicyRequest, EscalationPolicy> {

  @Override
  public EscalationPolicy convert(EscalationPolicyRequest escalationPolicyRequest) {
    return null;
  }
}
