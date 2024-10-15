package dk.nine.demo.repository.todos;

import dk.nine.demo.model.Todos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TodosRepository extends JpaRepository<Todos, UUID> {
    Optional<Todos> findByTitleContainsIgnoreCaseOrderByCreatedAt(String title);

}