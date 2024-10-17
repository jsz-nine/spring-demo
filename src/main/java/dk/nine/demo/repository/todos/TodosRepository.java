package dk.nine.demo.repository.todos;

import dk.nine.demo.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TodosRepository extends JpaRepository<TodoList, UUID> {
    List<TodoList> findByTitleContainsIgnoreCaseOrderByCreatedAt(String title);

}