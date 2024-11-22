package com.sid.LibraryManagement.exceptionHandlers;


import com.sid.LibraryManagement.exception.BookException;
import com.sid.LibraryManagement.exception.TxnException;
import com.sid.LibraryManagement.exception.UserException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handle(MethodArgumentNotValidException e){
        return  new ResponseEntity<>(e.getBindingResult().getFieldError().getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = TxnException.class)
    public ResponseEntity<Object> handleTxnException(TxnException txnException){
        return new ResponseEntity<>(txnException.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = BookException.class)
    public ResponseEntity<Object> handleBookException(BookException txnException){
        return new ResponseEntity<>(txnException.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = UserException.class)
    public ResponseEntity<Object> handleUserException(UserException txnException){
        return new ResponseEntity<>(txnException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
