package com.sid.LibraryManagement.repository;

import com.sid.LibraryManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    // search by via name

    // 3 ways to wirte the query
    // 1  Method (No query)
    // 2. Using @Query
    // 3. Using @Query with native false

    List<User> findByName(String name);
    List<User> findByNameLike(String name);

    @Query(value = "select u from User u where name=:name")
    List<User> retrieveUserViaName(String name);

    @Query(value = "select * from user where name=:name",nativeQuery = true)
    List<User> retrieveUserViaNameNative(String name);

    User findByEmail(String email);


}
