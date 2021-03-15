package catimageshop.Customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository repository) {
        return args -> {
           Customer nig = new Customer(
                   "nig",
                   "ger@mail.com",
                   LocalDate.of(2000, Month.MAY, 22)
           );

            Customer bla = new Customer(
                    "bla",
                    "ck@mail.com",
                    LocalDate.of(2001, Month.MAY, 22)
            );

            repository.saveAll(List.of(nig, bla));
        };
    }
}
