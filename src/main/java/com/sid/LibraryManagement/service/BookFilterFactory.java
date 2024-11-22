package com.sid.LibraryManagement.service;

import com.sid.LibraryManagement.enums.BookFilter;
import com.sid.LibraryManagement.service.impl.BookNoFilterImpl;
import com.sid.LibraryManagement.service.impl.BookTitleFilterImpl;
import com.sid.LibraryManagement.service.impl.BookTypeFilterImpl;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BookFilterFactory {

    private final Map<BookFilter,BookFilterStrategy> strategies = new HashMap<>();

    public BookFilterFactory(BookNoFilterImpl bookNoFilter, BookTitleFilterImpl bookTitleFilter, BookTypeFilterImpl bookTypeFilter){
        strategies.put(BookFilter.BOOK_NO,bookNoFilter);
        strategies.put(BookFilter.BOOK_TITLE,bookTitleFilter);
        strategies.put(BookFilter.BOOK_TYPE,bookTypeFilter);

    }

    public BookFilterStrategy getStrategy(BookFilter filter){
        return strategies.get(filter);
    }



}
