package com.sid.LibraryManagement.controller;

import com.sid.LibraryManagement.dto.request.TxnRequest;
import com.sid.LibraryManagement.dto.request.TxnReturnRequest;
import com.sid.LibraryManagement.exception.BookException;
import com.sid.LibraryManagement.exception.UserException;
import com.sid.LibraryManagement.service.impl.TxnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/txn")
@Validated
public class TxnController {

    @Autowired
    private TxnService txnService;

    @PostMapping("/issue")
    public String create(@RequestBody TxnRequest txnRequest) throws UserException, BookException {
        return txnService.create(txnRequest);
    }

    @PutMapping("/return")
    public Double returnTxn(@RequestBody TxnReturnRequest txnReturnRequest) throws BookException, UserException {
        return txnService.returnTxn(txnReturnRequest);
    }
}

//issue
//return
