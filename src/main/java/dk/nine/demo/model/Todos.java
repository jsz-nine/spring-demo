package dk.nine.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(schema = "internal", name = "todos")
public class Todos {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID uuid; // UUID as the primary key
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
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID(); // Generate a new UUID
        }
    }
}


