package com.spring.qa.auto.mock;

public class RestService {

    private final AuditService auditService;

    public RestService(AuditService auditService) {
        this.auditService = auditService;
    }

    public ResponseResult sendRequest(Object o) throws InterruptedException {
        Thread.sleep(2000);

        System.out.println("Request is successful");
        ResponseResult responseResult = new ResponseResult();
        responseResult.setReqRes(true);

        System.out.println("Send audit");
        boolean result = auditService.sendAudit(o);

        responseResult.setAuditRes(result);
        return responseResult;
    }
}
