package com.springpadrao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringPadraoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPadraoApplication.class, args);
    }

}
