package com.sid.LibraryManagement.entity;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AuthorCompositeKey {
    private String id;
    private String email;
}
