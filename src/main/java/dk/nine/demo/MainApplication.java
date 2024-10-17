package dk.nine.demo;

import dk.nine.demo.init.DatabasePopulate;
import dk.nine.demo.init.EnvironmentVariableLogger;
import dk.nine.demo.model.Company;
import dk.nine.demo.model.Person;
import dk.nine.demo.model.Task;
import dk.nine.demo.model.TodoList;
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
import java.util.List;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MainApplication.class);
        app.addListeners(new EnvironmentVariableLogger());
        app.run(args);
    }
}