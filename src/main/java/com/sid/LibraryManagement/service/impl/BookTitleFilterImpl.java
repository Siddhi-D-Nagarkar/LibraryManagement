package com.sid.LibraryManagement.service.impl;

import com.sid.LibraryManagement.dto.response.BookFilterResponse;
import com.sid.LibraryManagement.enums.Operator;
import com.sid.LibraryManagement.service.BookFilterStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookTitleFilterImpl implements BookFilterStrategy {
    @Override
    public List<BookFilterResponse> getFilteredBook(Operator operator, String value) {
        return List.of();
    }
}
