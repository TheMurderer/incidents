package com.aircall.test.Incidents.adapter.escpolicy;

import com.aircall.test.Incidents.adapter.escpolicy.vo.EscPolicy;
import com.aircall.test.Incidents.domain.EscalationPolicy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EscalationPolicyToEscPolicyConverter implements Converter<EscalationPolicy, EscPolicy> {

  @Override
  public EscPolicy convert(EscalationPolicy escalationPolicy) {
    return null;
  }
}
