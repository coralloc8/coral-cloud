package com.example.spring.web.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EntryTest extends AuthApplicationTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void encode() {
        for (int i = 0; i < 10; i++) {
            String password = passwordEncoder.encode("123456");
            log.info("password: {}", password);
        }

    }

}
