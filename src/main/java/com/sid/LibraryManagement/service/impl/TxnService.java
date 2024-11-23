package com.sid.LibraryManagement.service.impl;

import com.sid.LibraryManagement.dto.request.TxnRequest;
import com.sid.LibraryManagement.dto.request.TxnReturnRequest;
import com.sid.LibraryManagement.entity.Book;
import com.sid.LibraryManagement.entity.Txn;
import com.sid.LibraryManagement.entity.User;
import com.sid.LibraryManagement.enums.TxnStatus;
import com.sid.LibraryManagement.exception.BookException;
import com.sid.LibraryManagement.exception.TxnException;
import com.sid.LibraryManagement.exception.UserException;
import com.sid.LibraryManagement.repository.TxnRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TxnService {
    @Autowired
    private TxnRepository txnRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Value("${user.valid.days}")
    private int validUpto;

    @Value("${user.delayed.finePerDay}")
    private int finePerDay;

    @Transactional(rollbackFor = {BookException.class, UserException.class})
    public String create(TxnRequest txnRequest) throws UserException, BookException {

        // user trying to make txn is valid or not
        User userFromDb = userService.checkIfUserIsValid(txnRequest.getUserEmail());
        if(userFromDb == null){
            throw new UserException("User is not valid");
        }

        //book no present in my library
        Book bookFromDb = bookService.checkIfBookIsValid(txnRequest.getBookNo());
        if (bookFromDb == null){
            throw  new BookException("Book is not valid");
        }

        // Check if the book asked is assigned to another user
        if(bookFromDb.getUser() != null){
            throw  new BookException("Book is not free to be issued");
        }
        return createTxn(userFromDb,bookFromDb);
    }

    @Transactional
    public String createTxn(User userFromDb, Book bookFromDb) throws BookException {
        // create a txn
        String txnId = UUID.randomUUID().toString();
        Txn txn = Txn.
                builder().
                txnId(txnId).
                user(userFromDb).
                book(bookFromDb).
                txnStatus(TxnStatus.ISSUED).
                issuedDate(new Date()).
                build();
        txnRepository.save(txn);
        bookService.markBookUnavailable(bookFromDb, userFromDb);
        return txnId;
    }

    @Transactional(rollbackFor = {BookException.class, UserException.class})
    public Double returnTxn(TxnReturnRequest txnReturnRequest) throws BookException, UserException {
        // User is valid or not
        User userFromDb = userService.checkIfUserIsValid(txnReturnRequest.getUserEmail());
        if(userFromDb == null){
            throw new UserException("User is not valid");
        }
        //book no present in my library
        Book bookFromDb = bookService.checkIfBookIsValid(txnReturnRequest.getBookNo());
        if (bookFromDb == null){
            throw  new BookException("Book is not valid");
        }

        if(bookFromDb.getUser() != null && bookFromDb.getUser().equals(userFromDb)){
            Txn txnFromDb = txnRepository.findByTxnId(txnReturnRequest.getTxnId());
            if(txnFromDb == null){
                throw new TxnException("No Txn has been found in my db with this txnid");
            }

            Double amount = calculateSettlementAmount(txnFromDb,bookFromDb);
            if(amount == bookFromDb.getSecurityAmount()){
                txnFromDb.setTxnStatus(TxnStatus.RETURN);
            }else{
                txnFromDb.setTxnStatus(TxnStatus.FINED);
            }
            txnFromDb.setSettlementAmount(amount);

            // mark the book available
            bookFromDb.setUser(null);
            txnRepository.save(txnFromDb);


            return amount;
        }else {
            throw new TxnException("Book is assigned to someone else or not at all assigned ");
        }



    }

    public Double calculateSettlementAmount(Txn txnFromDb, Book bookFromDb) {
        long issueTime = txnFromDb.getIssuedDate().getTime();
        long returnTime = System.currentTimeMillis();
        long diff = returnTime-issueTime;
        int daysPassed = (int) TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);

        if(daysPassed > validUpto){
            int fineAmount= (daysPassed-validUpto)*finePerDay;
            return bookFromDb.getSecurityAmount()-fineAmount;
        }

        return bookFromDb.getSecurityAmount();
    }
}
