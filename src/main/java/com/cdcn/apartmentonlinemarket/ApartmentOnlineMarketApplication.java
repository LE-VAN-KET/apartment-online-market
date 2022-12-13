package com.cdcn.apartmentonlinemarket;

import com.cdcn.apartmentonlinemarket.products.services.FilesStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class ApartmentOnlineMarketApplication implements CommandLineRunner {
    @Resource
     FilesStorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(ApartmentOnlineMarketApplication.class, args);
    }

    @Override
    public void run(String... arg) {
        storageService.init();
    }

}
