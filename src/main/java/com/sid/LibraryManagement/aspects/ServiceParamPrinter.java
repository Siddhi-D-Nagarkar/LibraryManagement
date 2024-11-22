package com.sid.LibraryManagement.aspects;


import com.sid.LibraryManagement.dto.request.TxnRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceParamPrinter {

    private static final Logger log = LoggerFactory.getLogger(ServiceParamPrinter.class);

    @Pointcut("execution(* com.sid.LibraryManagement.service.impl.TxnService.create(com.sid.LibraryManagement.dto.request.TxnRequest))")
    public void txnServiceCreateMethod() {}


    @Before(value = "txnServiceCreateMethod() && args(txnRequest)")
    public void txnServiceAdvice(TxnRequest txnRequest){
        log.info("TxnRequest :- "+txnRequest);
    }

}
