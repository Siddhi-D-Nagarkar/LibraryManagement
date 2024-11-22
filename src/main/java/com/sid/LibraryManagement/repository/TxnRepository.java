package com.sid.LibraryManagement.repository;

import com.sid.LibraryManagement.entity.Txn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TxnRepository extends JpaRepository<Txn,Integer> {
    Txn findByTxnId(String txnId);
}
