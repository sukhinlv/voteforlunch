package ru.jsft.voteforlunch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class VoteforlunchApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoteforlunchApplication.class, args);
    }

}
