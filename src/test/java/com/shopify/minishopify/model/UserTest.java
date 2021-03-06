package com.shopify.minishopify.model;

import com.shopify.minishopify.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void prepareDB() {
        userRepository.deleteAll();
    }

    @Test
    public void testPersistence() {
        User testUser = new User("TEST_USER", "JUNIT");
        userRepository.save(testUser);

        List<User> dbUsers = userRepository.findAll();
        assertEquals(dbUsers.size(), 1);
        User readUser = dbUsers.get(0);
        assertEquals("TEST_USER", readUser.getName());
        assertEquals("JUNIT", readUser.getEmail());

        userRepository.delete(readUser);
        dbUsers = userRepository.findAll();
        assertEquals(dbUsers.size(), 0);
    }
}
