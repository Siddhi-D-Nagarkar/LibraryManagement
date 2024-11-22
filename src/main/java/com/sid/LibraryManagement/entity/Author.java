package com.sid.LibraryManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(AuthorCompositeKey.class)
public class Author extends TimeStamps{

    @Id
    private String id;

    // We have made this as unique identifier
    @Id
    @Column(nullable = false,unique = true,length = 50)
    private String email;

    @Column(length = 30)
    private String name;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private List<Book> bookList;

}

// We want to make composite key
// There are two ways for make composite key

