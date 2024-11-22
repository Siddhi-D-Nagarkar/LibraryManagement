package com.sid.LibraryManagement.service;

import com.sid.LibraryManagement.dto.response.BookFilterResponse;
import com.sid.LibraryManagement.enums.Operator;

import java.util.List;

public interface BookFilterStrategy {
    List<BookFilterResponse> getFilteredBook(Operator operator, String value);
}
