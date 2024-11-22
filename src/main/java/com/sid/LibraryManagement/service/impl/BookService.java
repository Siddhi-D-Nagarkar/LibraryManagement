package com.sid.LibraryManagement.service.impl;

import com.sid.LibraryManagement.dto.request.BookCreationRequest;
import com.sid.LibraryManagement.dto.response.BookCreationResponse;
import com.sid.LibraryManagement.dto.response.BookFilterResponse;
import com.sid.LibraryManagement.entity.Author;
import com.sid.LibraryManagement.entity.Book;
import com.sid.LibraryManagement.entity.User;
import com.sid.LibraryManagement.enums.BookFilter;
import com.sid.LibraryManagement.enums.Operator;
import com.sid.LibraryManagement.repository.BookRepository;
import com.sid.LibraryManagement.service.BookFilterFactory;
import com.sid.LibraryManagement.service.BookFilterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookFilterFactory bookFilterFactory;

    public BookCreationResponse addBook(BookCreationRequest request) {

        Author authorFromDb = authorService.findAuthorInDB(request.getAuthorEmail());

//        if (authorFromDb == null){
//            authorFromDb = authorService.saveMyAuthor(Author.builder()
//                            .id(UUID.randomUUID().toString())
//                            .email(request.getAuthorEmail())
//                            .name(request.getAuthorName())
//                    .build());
//        }
        // Create Book from Request
        Book book = request.ToBook();
        if(authorFromDb == null){
            authorFromDb = Author.builder()
                            .id(UUID.randomUUID().toString())
                            .email(request.getAuthorEmail())
                            .name(request.getAuthorName())
                            .build();
        }
        book.setAuthor(authorFromDb);
        Book bookFromDB =  bookRepository.save(book);
        return BookCreationResponse.builder()
                .bookName(bookFromDB.getTitle())
                .bookNo(bookFromDB.getBookNo())
                .securityAmount(bookFromDB.getSecurityAmount())
                .build();

    }

    public List<BookFilterResponse> filter(BookFilter filterBy, Operator operator, String value) {
        BookFilterStrategy bookFilterStrategy =  bookFilterFactory.getStrategy(filterBy);
        return bookFilterStrategy.getFilteredBook(operator,value);
    }

    public Book checkIfBookIsValid(String bookNo) {
        List<Book> books =  bookRepository.findByBookNo(bookNo);
        if(books.isEmpty()){
            return null;
        }

        return books.get(0);
    }

    public void markBookUnavailable(Book bookFromDb, User userFromDb) {
        bookFromDb.setUser(userFromDb);
        bookRepository.save(bookFromDb);
    }
}
