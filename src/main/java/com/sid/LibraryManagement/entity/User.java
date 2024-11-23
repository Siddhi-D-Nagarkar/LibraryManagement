package com.sid.LibraryManagement.entity;

import com.sid.LibraryManagement.enums.UserStatus;
import com.sid.LibraryManagement.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"USER\"")
public class User extends TimeStamps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String name ;


    @Column(unique = true,length = 15)
    private String phoneNo;

    // We have made this as unique identifier
    @Column(nullable = false,unique = true,length = 50)
    private String email;

    private String address;

    @Enumerated
    private UserStatus userStatus;

    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    @OneToMany(mappedBy = "user")
    private List<Book> bookList;

    @OneToMany(mappedBy = "user")
    private List<Txn> txnList;

}
