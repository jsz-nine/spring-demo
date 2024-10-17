package dk.nine.demo;

import dk.nine.demo.init.EnvironmentVariableLogger;
import dk.nine.demo.model.Company;
import dk.nine.demo.model.Person;
import dk.nine.demo.repository.CompanyRepository;
import dk.nine.demo.repository.PersonRepository;
import dk.nine.demo.repository.todos.TodosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;

@SpringBootApplication
public class MainApplication {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private PersonRepository personRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            if (companyRepository.findAll().isEmpty()) {
                Company nine = Company.builder().name("Nine A/S").build();
                companyRepository.save(nine);
            }
            if (personRepository.findAll().isEmpty()) {
                Person philip = Person.builder()
                        .firstname("Philip")
                        .lastname("Cronqvist Mouridsen")
                        .birthday(LocalDate.of(1996, 1, 21))
                        .build();
                Person Jonathan = Person.builder()
                        .firstname("Jonathan")
                        .lastname("Szigethy")
                        .birthday(LocalDate.of(1993, 2, 22))
                        .build();
                personRepository.save(philip);
                personRepository.save(Jonathan);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MainApplication.class);
        app.addListeners(new EnvironmentVariableLogger());
        app.run(args);
    }
}