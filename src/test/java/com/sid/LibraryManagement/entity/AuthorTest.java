package com.sid.LibraryManagement.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AuthorTest {

    @Test
    public void testGetEmail(){
        Author author = new Author();
        author.setEmail("sid@gmail.com");
        assertEquals("sid@gmail.com",author.getEmail());
    }
}
