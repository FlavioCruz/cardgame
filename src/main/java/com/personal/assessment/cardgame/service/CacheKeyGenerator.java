package com.personal.assessment.cardgame.service;

import org.springframework.stereotype.Service;

@Service
public class CacheKeyGenerator {

    public String generateKey(Long movie1Id, Long movie2Id) {
        return movie1Id < movie2Id
                ? movie1Id + "-" + movie2Id
                : movie2Id + "-" + movie1Id;
    }
}
