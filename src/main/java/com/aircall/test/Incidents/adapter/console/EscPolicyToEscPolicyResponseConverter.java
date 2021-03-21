package com.aircall.test.Incidents.adapter.console;

import com.aircall.test.Incidents.adapter.console.vo.EscalationPolicyResponse;
import com.aircall.test.Incidents.domain.EscalationPolicy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EscPolicyToEscPolicyResponseConverter implements Converter<EscalationPolicy, EscalationPolicyResponse> {

  @Override
  public EscalationPolicyResponse convert(EscalationPolicy escPolicy) {
    return null;
  }
}
