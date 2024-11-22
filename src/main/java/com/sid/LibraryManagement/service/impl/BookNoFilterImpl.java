package com.sid.LibraryManagement.service.impl;

import com.sid.LibraryManagement.dto.response.BookFilterResponse;
import com.sid.LibraryManagement.entity.Book;
import com.sid.LibraryManagement.enums.Operator;
import com.sid.LibraryManagement.repository.BookRepository;
import com.sid.LibraryManagement.service.BookFilterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookNoFilterImpl implements BookFilterStrategy {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookFilterResponse> getFilteredBook(Operator operator, String value) {
        if(!operator.equals(Operator.EQUALS)){
            throw new IllegalArgumentException(("Only Equals is expected with book no "));
        }
        List<Book> books =  bookRepository.findByBookNo(value);

        return books.
                stream().
                map(book -> convertToBookFilterResponse(book)).
                collect(Collectors.toList());


    }

    private BookFilterResponse convertToBookFilterResponse(Book book) {
        return BookFilterResponse.
                builder().
                bookNo(book.getBookNo())
                .authorEmail(book.getAuthor().getEmail())
                .authorName(book.getAuthor().getName())
                .bookType(book.getBookType())
                .bookName(book.getTitle())
                .build();
    }
}
