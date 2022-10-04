package com.spring.qa.auto.mock;

public class AuditService {

    public boolean sendAudit(Object o) throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("Object " + o + " has been sent to audit");
        return true;
    }
}
