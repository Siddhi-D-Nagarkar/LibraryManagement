package com.sid.LibraryManagement.entity;

import com.sid.LibraryManagement.enums.BookType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Book extends TimeStamps{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String title;

    // This is unique identifier
    @Column(length = 20,unique = true)
    private String bookNo;

    @Enumerated
    private BookType bookType;

    @Column(nullable = false)
    private Double securityAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "author_id",referencedColumnName = "id"),
            @JoinColumn(name = "author_name",referencedColumnName = "email")
    })
    private Author author;

    @OneToMany(mappedBy = "book")
    private List<Txn> txnList;



}
