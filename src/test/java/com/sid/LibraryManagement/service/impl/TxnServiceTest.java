package com.sid.LibraryManagement.service.impl;


import com.sid.LibraryManagement.dto.request.TxnRequest;
import com.sid.LibraryManagement.entity.Book;
import com.sid.LibraryManagement.entity.Txn;
import com.sid.LibraryManagement.entity.User;
import com.sid.LibraryManagement.exception.BookException;
import com.sid.LibraryManagement.exception.UserException;
import com.sid.LibraryManagement.repository.TxnRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TxnServiceTest {

    @Mock
    private TxnRepository txnRepository;

    @Mock
    private UserService userService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private TxnService txnService;

    @Test
    public void testCreateNullUser() throws BookException, UserException {
        TxnRequest txnRequest  = TxnRequest.builder().bookNo("bookNo").userEmail("email").build();
        when(userService.checkIfUserIsValid(any())).thenReturn(null);
        assertThrows(UserException.class , () -> txnService.create(txnRequest));
    }

    @Test
    public void testCreateNullBook() throws BookException, UserException {
        TxnRequest txnRequest  = TxnRequest.builder().bookNo("bookNo").userEmail("email").build();
        User user = User.builder().id(1).email("test@gmail.com").build();

        when(userService.checkIfUserIsValid(any())).thenReturn(user);
        when(bookService.checkIfBookIsValid(any())).thenReturn(null);

        assertThrows(BookException.class , () -> txnService.create(txnRequest));
    }




    @Test
    public void testCalculateSettlementAmount() throws ParseException {
        ReflectionTestUtils.setField(txnService, "validUpto", 10);
        ReflectionTestUtils.setField(txnService, "finePerDay", 1);
        Date issueDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-11-23 12:12:12");
        Txn txn = Txn.builder().txnId("1").issuedDate(issueDate).build();
        Book book = Book.builder().securityAmount(100.0).id(1).build();

        assertEquals(79.0, txnService.calculateSettlementAmount(txn, book));

    }
}



//TODO Write the Test Case by making the calculateSettlementAmount private
