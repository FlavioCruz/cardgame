package com.personal.assessment.cardgame.service.impl;

import com.personal.assessment.cardgame.dto.NewUserDto;
import com.personal.assessment.cardgame.model.User;
import com.personal.assessment.cardgame.repository.UserRepository;
import com.personal.assessment.cardgame.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String registerUser(NewUserDto newUser) {
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            return "Username already exists";
        }
        User user = new User.Builder()
                .setPassword(passwordEncoder.encode(newUser.getPassword()))
                .setUsername(newUser.getUsername())
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }
}
