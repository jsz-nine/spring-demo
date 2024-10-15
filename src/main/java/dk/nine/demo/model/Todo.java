package dk.nine.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(schema = "internal", name = "todo")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDate createdAt;
    private LocalDate dueDate;
    private Boolean completed;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todos_uuid", nullable = false) // Foreign key column
    private Todos todoList; // Reference to Todos



}


