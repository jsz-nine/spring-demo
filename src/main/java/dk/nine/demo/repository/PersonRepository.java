package dk.nine.demo.repository;

import dk.nine.demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT p FROM Person p WHERE " +
            "LOWER(p.firstname) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.lastname) LIKE LOWER(CONCAT('%', :query, '%'))")
    Set<Person> findPersonByQuery(@Param("query") String query);

/*
List<Person> findByTitleContainsIgnoreCaseOrderByCreatedAt(String firstname);
 */
}