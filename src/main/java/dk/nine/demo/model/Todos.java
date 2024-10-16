package dk.nine.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import dk.nine.demo.view.ModelResource;
import dk.nine.demo.view.DtoResource;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(schema = "internal", name = "todos")
public class Todos implements ModelResource<UUID>, DtoResource<UUID> {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id; // UUID as the primary key
    private String title;
    private String description;

    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Todo> todoList = new ArrayList<Todo>(); // Initialize to avoid null issues

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdAt;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate completedAt;

    @PrePersist
    private void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID(); // Generate a new UUID
        }
    }
}


