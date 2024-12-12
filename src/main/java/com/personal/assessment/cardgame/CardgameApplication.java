package com.personal.assessment.cardgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CardgameApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardgameApplication.class, args);
    }

}
