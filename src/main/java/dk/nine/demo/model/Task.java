package dk.nine.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import dk.nine.demo.view.ModelResource;
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
public class Task implements ModelResource<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdAt;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dueDate;
    @Builder.Default
    private Boolean completed = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todos_uuid", nullable = false) // Foreign key column
    private TodoList taskList; // Reference to Todos


}


