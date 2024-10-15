package dk.nine.demo.repository.todos;

import dk.nine.demo.model.Todos;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TodosRepository extends JpaRepository<Todos, UUID> {
import java.util.Optional;
    List<Todos> findByTitleContainsIgnoreCaseOrderByCreatedAt(String title);

}