package dk.nine.demo.repository.todos;

import dk.nine.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Task, Long> {
}
