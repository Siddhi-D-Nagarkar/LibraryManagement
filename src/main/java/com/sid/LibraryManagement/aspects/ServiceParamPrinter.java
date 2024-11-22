package com.sid.LibraryManagement.aspects;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceParamPrinter {

    @Pointcut("execution(* com.sid.LibraryManagement.service.impl.TxnService.create(com.sid.LibraryManagement.dto.request.TxnRequest))")
    public void txnServiceCreateMethod() {}


    @Before(value = "txnServiceCreateMethod() && args()")

}
