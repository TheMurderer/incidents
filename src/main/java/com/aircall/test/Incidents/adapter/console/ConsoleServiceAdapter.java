package com.aircall.test.Incidents.adapter.console;

import com.aircall.test.Incidents.adapter.console.vo.EscalationPolicyRequest;
import com.aircall.test.Incidents.adapter.console.vo.EscalationPolicyResponse;
import com.aircall.test.Incidents.port.ConsoleService;
import com.aircall.test.Incidents.port.EscPolicyService;
import com.aircall.test.Incidents.service.api.PagerService;
import org.springframework.stereotype.Component;

@Component
public class ConsoleServiceAdapter implements ConsoleService<EscalationPolicyRequest, EscalationPolicyResponse> {

  private final PagerService pagerService;
  private final EscPolicyService escPolicyService;
  private final EscPolicyRequestToEscPolicyConverter requestToPolicyConverter;
  private final EscPolicyToEscPolicyResponseConverter policyToResponseConverter;

  public ConsoleServiceAdapter(PagerService pagerService, EscPolicyService escPolicyService,
      EscPolicyRequestToEscPolicyConverter requestToPolicyConverter,
      EscPolicyToEscPolicyResponseConverter policyToResponseConverter) {
    this.pagerService = pagerService;
    this.escPolicyService = escPolicyService;
    this.requestToPolicyConverter = requestToPolicyConverter;
    this.policyToResponseConverter = policyToResponseConverter;
  }

  @Override
  public void sendAck(Integer serviceId) {

  }

  @Override
  public void sendHealthyStatus(Integer serviceId) {

  }

  @Override
  public EscalationPolicyResponse getEscPolicyToService(Integer serviceId) {
    return null;
  }

  @Override
  public EscalationPolicyResponse getEscPolicy(Integer escPolicyId) {
    return null;
  }

  @Override
  public void modifyEscPolicy(EscalationPolicyRequest escalationPolicyRequest) {

  }

}
