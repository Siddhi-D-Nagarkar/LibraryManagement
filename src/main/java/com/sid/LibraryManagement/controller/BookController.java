package com.sid.LibraryManagement.controller;

import com.sid.LibraryManagement.dto.GenericReturnClass;
import com.sid.LibraryManagement.dto.request.BookCreationRequest;
import com.sid.LibraryManagement.dto.response.BookCreationResponse;
import com.sid.LibraryManagement.dto.response.BookFilterResponse;
import com.sid.LibraryManagement.enums.BookFilter;
import com.sid.LibraryManagement.enums.Operator;
import com.sid.LibraryManagement.service.impl.BookService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @PostMapping("/addBook")
    public ResponseEntity<GenericReturnClass> addBook(@RequestBody  @Validated BookCreationRequest request){
        BookCreationResponse response = bookService.addBook(request);
        GenericReturnClass returnObject = GenericReturnClass.builder().data(response).build();
        if(response != null){
            returnObject.setCode(0);
            returnObject.setMsg("Its successful");
        }else{
            returnObject.setCode(1);
            returnObject.setMsg("Its failed");
        }
        return new ResponseEntity<>(returnObject, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public List<BookFilterResponse> filterBook(@NotNull(message = "filterBy must not be null") @RequestParam("filterBy") BookFilter filterBy,
                                               @NotNull(message = "operator must not be null") @RequestParam("operator") Operator operator,
                                               @NotBlank(message = "value must not be blank") @RequestParam("value") String value){
        return bookService.filter(filterBy,operator,value);

    }

    
}

//create
// filter
//UPdate / delete homework
