package com.aircall.test.Incidents.adapter.console;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.aircall.test.Incidents.adapter.console.vo.EscalationPolicyRequest;
import com.aircall.test.Incidents.adapter.console.vo.EscalationPolicyResponse;
import com.aircall.test.Incidents.adapter.console.vo.LevelRequest;
import com.aircall.test.Incidents.adapter.console.vo.LevelResponse;
import com.aircall.test.Incidents.adapter.console.vo.TargetRequest;
import com.aircall.test.Incidents.adapter.console.vo.TargetResponse;
import com.aircall.test.Incidents.domain.EscalationPolicy;
import com.aircall.test.Incidents.domain.Target;
import com.aircall.test.Incidents.domain.TargetType;
import com.aircall.test.Incidents.port.EscPolicyService;
import com.aircall.test.Incidents.service.api.PagerService;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class ConsoleServiceAdapterTest {

  private ConsoleServiceAdapter adapter;
  private PagerService pagerService;
  private EscPolicyService escPolicyService;
  private EscPolicyRequestToEscPolicyConverter requestToPolicyConverter;
  private EscPolicyToEscPolicyResponseConverter policyToResponseConverter;

  @BeforeEach
  void setUp() {
    pagerService = mock(PagerService.class);
    escPolicyService = mock(EscPolicyService.class);
    requestToPolicyConverter = mock(EscPolicyRequestToEscPolicyConverter.class);
    policyToResponseConverter = mock(EscPolicyToEscPolicyResponseConverter.class);

    adapter = new ConsoleServiceAdapter(pagerService, escPolicyService,requestToPolicyConverter, policyToResponseConverter);
  }

  private EscalationPolicy generateEP(){
    Target target = new Target.Builder().withId(1).withType(TargetType.EMAIL).withReference("raul.informatico.sound@gmail.com").build();
    HashMap<Integer, List<Target>> levels = new HashMap<>();
    levels.put(1, List.of(target));
    return new EscalationPolicy.Builder().withId(1).withLevels(levels).build();
  }

  private EscalationPolicyResponse generateEPResponse(){
    TargetResponse target = new TargetResponse.Builder().withId(1).withType(TargetType.EMAIL).withReference("raul.informatico.sound@gmail.com").build();

    LevelResponse level = new LevelResponse.Builder().withId(1).withTarget(List.of(target)).build();
    return new EscalationPolicyResponse.Builder().withId(1).withLevels(List.of(level)).build();
  }

  private EscalationPolicyRequest generateEPRequest(){
    TargetRequest
        target = new TargetRequest.Builder().withId(1).withType(TargetType.EMAIL).withReference("raul.informatico.sound@gmail.com").build();

    LevelRequest level = new LevelRequest.Builder().withId(1).withTarget(List.of(target)).build();
    return new EscalationPolicyRequest.Builder().withId(1).withLevels(List.of(level)).build();
  }

  @Test
  public void whenSendAckThenPagerServiceIsInvoked(){

    // act
    adapter.sendAck(1);

    // assert
    verify(pagerService).processAck(1);
  }

  @Test
  public void whenSendHealthyStatusThenPagerServiceIsInvoked(){

    // act
    adapter.sendHealthyStatus(1);

    // assert
    verify(pagerService).processHealthy(1);
  }

  @Test
  public void whenGetEscPolicyToServiceThenEscPolicyIsInvoked(){
    // arrange
    EscalationPolicy ep = generateEP();
    EscalationPolicyResponse epResponse = generateEPResponse();

    when(escPolicyService.getEscPolicyToSpecificService(1)).thenReturn(ep);
    when(policyToResponseConverter.convert(ep)).thenReturn(epResponse);

    // act
    EscalationPolicyResponse target = adapter.getEscPolicy(1);

    // assert
    verify(escPolicyService).getEscPolicyToSpecificService(1);
    verify(policyToResponseConverter).convert(ep);
    assertNotNull(target);
    assertEquals(epResponse,target);
  }

  @Test
  public void whenGetEscPolicyThenEscPolicyIsInvoked(){
    // arrange
    EscalationPolicy ep = generateEP();
    EscalationPolicyResponse epResponse = generateEPResponse();

    when(escPolicyService.getEscPolicy(1)).thenReturn(ep);
    when(policyToResponseConverter.convert(ep)).thenReturn(epResponse);

    // act
    EscalationPolicyResponse target = adapter.getEscPolicy(1);

    // assert
    verify(escPolicyService).getEscPolicy(1);
    verify(policyToResponseConverter).convert(ep);
    assertNotNull(target);
    assertEquals(epResponse,target);
  }

  @Test
  public void whenModifyEscPolicyThenEscPolicyIsInvoked(){
    // arrange
    EscalationPolicy ep = generateEP();
    EscalationPolicyRequest epRequest = generateEPRequest();

    when(requestToPolicyConverter.convert(epRequest)).thenReturn(ep);

    // act
    adapter.modifyEscPolicy(epRequest);

    // assert
    verify(requestToPolicyConverter).convert(epRequest);
    verify(escPolicyService).modifyEscPolicy(ep);

  }
}