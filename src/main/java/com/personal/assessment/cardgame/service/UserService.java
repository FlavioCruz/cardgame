package com.personal.assessment.cardgame.service;

import com.personal.assessment.cardgame.dto.NewUserDto;

public interface UserService {
    String registerUser(NewUserDto user);
}
