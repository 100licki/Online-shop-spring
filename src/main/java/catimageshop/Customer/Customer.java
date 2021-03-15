package catimageshop.Customer;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Transient
    @Column(name = "age")
    private Integer age;

    public Customer() {
    }

    public Customer(String name, String email, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }
}
