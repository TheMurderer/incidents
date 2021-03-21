package com.aircall.test.Incidents.adapter.escpolicy;

import com.aircall.test.Incidents.adapter.escpolicy.vo.EscPolicy;
import com.aircall.test.Incidents.domain.EscalationPolicy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EscPolicyToEscalationPolicyConverter implements Converter<EscPolicy, EscalationPolicy> {

  @Override
  public EscalationPolicy convert(EscPolicy escPolicy) {
    return null;
  }
}
