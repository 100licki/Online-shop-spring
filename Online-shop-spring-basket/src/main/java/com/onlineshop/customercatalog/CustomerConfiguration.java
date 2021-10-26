package com.onlineshop.customercatalog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class CustomerConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository repository) {
        return args -> {
            var white = new Customer(
                    "white",
                    "white@mail.com",
                    LocalDate.of(2000, Month.MAY, 22)
            );

            var black = new Customer(
                    "black",
                    "black@mail.com",
                    LocalDate.of(2001, Month.MAY, 22)
            );

            repository.saveAll(List.of(white, black));
        };
    }
}
