package com.sid.LibraryManagement.dto.request;


import com.sid.LibraryManagement.entity.Book;
import com.sid.LibraryManagement.enums.BookType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookCreationRequest {
    @NotBlank(message = "Book Title must not be blank")
    private String bookTitle;
    @Positive(message = "Positive Values are epxected")
    private Double securityAmount;
    @NotBlank(message = "Book Title must not be blank")
    private String bookNo;
    @NotNull(message = "Book Type must not be null")
    private BookType bookType;
    @NotBlank(message = "Author name must not be blank")
    private String authorName;
    @NotBlank(message = "Author Email Title must not be blank")
    private String authorEmail;

    public Book ToBook() {
        return Book.builder()
                .title(this.bookTitle)
                .securityAmount(this.securityAmount)
                .bookNo(this.bookNo)
                .bookType(this.bookType)
                .build();
    }
}
