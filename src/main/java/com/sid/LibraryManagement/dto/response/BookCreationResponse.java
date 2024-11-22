package com.sid.LibraryManagement.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCreationResponse {
    private String bookNo;
    private String bookName;
    private Double securityAmount;
}
