package com.spring.qa.auto.mock;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RestServiceMockTest {

    @Spy
    AuditService auditServiceSpy = new AuditService();

    @Mock
    AuditService auditServiceMock = new AuditService();

    @Test
    void mockAuditTest() throws InterruptedException {
        AuditService auditService = Mockito.mock(AuditService.class);
        Mockito.when(auditService.sendAudit(Mockito.any())).thenReturn(false);
        RestService restService = new RestService(auditService);
        ResponseResult responseResult = restService.sendRequest(new Object());
        Assertions.assertThat(responseResult.isAuditRes()).isFalse();
    }

    @Test
    void mockAuditWithAnnotationTest() throws InterruptedException {
        Mockito.when(auditServiceMock.sendAudit(Mockito.any())).thenReturn(false);
        RestService restService = new RestService(auditServiceMock);
        ResponseResult responseResult = restService.sendRequest(new Object());
        Assertions.assertThat(responseResult.isAuditRes()).isFalse();
    }

    @Test
    void spyAuditTest() throws InterruptedException {
        AuditService auditService = Mockito.spy(AuditService.class);
        RestService restService = new RestService(auditService);
        ResponseResult responseResult = restService.sendRequest(new Object());
        Assertions.assertThat(responseResult.isAuditRes()).isTrue();
        verify(auditService, atLeastOnce()).sendAudit(Mockito.any());
    }

    @Test
    void spyAuditWithAnnotationTest() throws InterruptedException {
        RestService restService = new RestService(auditServiceSpy);
        ResponseResult responseResult = restService.sendRequest(new Object());
        Assertions.assertThat(responseResult.isAuditRes()).isTrue();
        verify(auditServiceSpy, atLeastOnce()).sendAudit(Mockito.any());
    }
}
