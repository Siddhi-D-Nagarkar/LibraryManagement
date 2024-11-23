package com.sid.LibraryManagement.repository;

import com.sid.LibraryManagement.entity.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
@DataJpaTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb", "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    @BeforeEach
    public void setup(){
        Author author =  Author.builder().id("1").email("janvi@gmail.com").name("JANVI").build();
        authorRepository.save(author);
        authorRepository.save(Author.builder().id("2").email("sid@gmail.com").name("SID").build());
    }

    @Test
    public void testFindByEmail(){
        Author author = authorRepository.findByEmail("janvi@gmail.com");
        assertEquals(author.getName(),"JANVI");
    }
}
