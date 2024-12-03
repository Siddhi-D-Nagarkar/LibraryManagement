package com.sid.LibraryManagement.repository;

import com.sid.LibraryManagement.entity.Author;
import com.sid.LibraryManagement.entity.AuthorCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, AuthorCompositeKey> {

    Author findByEmail(String email);



}
