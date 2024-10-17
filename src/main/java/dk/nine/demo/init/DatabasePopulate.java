package dk.nine.demo.init;

import dk.nine.demo.model.Company;
import dk.nine.demo.model.Person;
import dk.nine.demo.model.TodoList;
import dk.nine.demo.repository.CompanyRepository;
import dk.nine.demo.repository.PersonRepository;
import dk.nine.demo.repository.todos.TodosRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatabasePopulate implements CommandLineRunner {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    TodosRepository todosRepository;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (companyRepository.findAll().isEmpty()) {
            Company nine = Company.builder().name("Nine A/S").build();
            companyRepository.save(nine);
        }
        if (personRepository.findAll().isEmpty()) {
            Person philip = Person.builder().firstname("Philip").lastname("Cronqvist Mouridsen").birthday(LocalDate.of(1996, 1, 21)).build();
            Person Jonathan = Person.builder().firstname("Jonathan").lastname("Szigethy").birthday(LocalDate.of(1993, 2, 22)).build();
            personRepository.save(philip);
            personRepository.save(Jonathan);
        }
        if (todosRepository.findAll().isEmpty()) {
//            List<Task> makeFrontendForTodoListApp = List.of(Task.builder()
//                    .title("Make Frontend for TodoList App")
//                    .completed(false)
//                    .description("This is to preview the spring-demo application")
//                    .dueDate(LocalDate.now().plusDays(5))
//                    .build());
            TodoList todoList = TodoList.builder().title("Philips TodoList").createdAt(LocalDate.now()).description("This is a list of tasks, for Philip").build();
            todosRepository.save(todoList);
        }

    }
}
