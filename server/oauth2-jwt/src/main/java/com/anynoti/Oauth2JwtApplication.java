package com.anynoti;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {
    "com.anynoti",
})
@EnableJpaRepositories(basePackages = "com.anynoti")
public class Oauth2JwtApplication {
    public static void main(String[] args) {
        SpringApplication.run(Oauth2JwtApplication.class, args);
    }

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

}