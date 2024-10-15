package dk.nine.demo.dto.todo;


import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class TodoDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate createdAt;
    private LocalDate dueDate;
    private Boolean completed;
}
