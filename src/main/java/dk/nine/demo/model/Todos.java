package dk.nine.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(schema = "internal", name = "todos")
public class Todos {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID uuid; // UUID as the primary key
    private String title;
    private String description;
    @OneToMany(mappedBy = "todos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Todo> todos;
    private LocalDate createdAt;
    private LocalDate completedAt;

    @PrePersist
    private void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID(); // Generate a new UUID
        }
    }
}


