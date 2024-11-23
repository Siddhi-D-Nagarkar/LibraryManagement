package com.sid.LibraryManagement.repository;


import com.sid.LibraryManagement.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb", "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void setup(){
//        User user = User.builder().
//                email("geek@gmail.com").
//                name("GeekForGeeks").
//                build();
//
//        userRepository.save(user);

        User user1 = User.builder().
                email("airtel@gmail.com").
                name("AirtelPayment").
                build();
        userRepository.save(user1);
        User user2 = User.builder().
                email("airtelRefund@gmail.com").
                name("AirtelPayment").
                build();
        userRepository.save(user2);

    }

    @Test
    public void testFindByEmail(){
        assertEquals(userRepository.findByEmail("geek@gmail.com").getName(),"GeekForGeeks");
    }

    @Test
    public void testFindByName(){
        List<User> users = userRepository.findByName("AirtelPayment");
        List<User> expectedList = new ArrayList<>();
        User u1 = User.builder().id(1).name("AirtelPayment").email("airtel@gmail.com").build();
        User u2 = User.builder().id(2).name("AirtelPayment").email("airtelRefund@gmail.com").build();
        expectedList.add(u1);
        expectedList.add(u2);
        assertEquals(expectedList.size(), users.size());
    }
}
