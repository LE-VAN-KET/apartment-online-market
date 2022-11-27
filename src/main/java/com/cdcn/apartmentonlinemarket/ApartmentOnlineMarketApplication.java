package com.cdcn.apartmentonlinemarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.UUID;

@SpringBootApplication
@EnableJpaAuditing
public class ApartmentOnlineMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApartmentOnlineMarketApplication.class, args);
    }

}
