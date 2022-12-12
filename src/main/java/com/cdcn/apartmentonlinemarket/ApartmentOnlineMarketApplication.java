package com.cdcn.apartmentonlinemarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.UUID;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class ApartmentOnlineMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApartmentOnlineMarketApplication.class, args);
    }

}
