package com.aircall.test.Incidents.port;

public interface ConsoleService<REQ, RES> {

  void sendAck(Integer serviceId);

  void sendHealthyStatus(Integer serviceId);

  RES getEscPolicyToService(Integer serviceId);

  RES getEscPolicy(Integer escPolicyId);

  void modifyEscPolicy(REQ escalationPolicyRequest);


}
