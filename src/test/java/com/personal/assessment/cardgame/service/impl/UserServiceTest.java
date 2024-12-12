package com.personal.assessment.cardgame.service.impl;

import com.personal.assessment.cardgame.dto.NewUserDto;
import com.personal.assessment.cardgame.model.User;
import com.personal.assessment.cardgame.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;
    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void shouldCreateUser_withSuccess() {
        NewUserDto newUserDto = new NewUserDto.Builder()
                .setUsername("user")
                .setPassword("pass")
                .build();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        String result = userService.registerUser(newUserDto);
        assertEquals("User registered successfully", result);
    }

    @Test
    public void shouldNotCreateUser_withMessage() {
        NewUserDto newUserDto = new NewUserDto.Builder()
                .setUsername("user")
                .setPassword("pass")
                .build();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User.Builder().build()));

        String result = userService.registerUser(newUserDto);
        assertEquals("Username already exists", result);
    }
}
