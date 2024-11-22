package com.sid.LibraryManagement.entity;

import com.sid.LibraryManagement.enums.TxnStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Txn extends TimeStamps{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String txnId;

    @Enumerated(value = EnumType.STRING)
    private TxnStatus txnStatus;

    private Double settlementAmount;

    private Date issuedDate;

    private Date submitted;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Book book;

}
