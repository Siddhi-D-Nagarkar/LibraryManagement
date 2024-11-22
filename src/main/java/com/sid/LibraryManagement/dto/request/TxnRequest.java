package com.sid.LibraryManagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TxnRequest {
    @NotBlank(message = "User email must not be blank")
    private String userEmail;

    @NotBlank(message = "Book Number must not be blank")
    private String bookNo;
}
