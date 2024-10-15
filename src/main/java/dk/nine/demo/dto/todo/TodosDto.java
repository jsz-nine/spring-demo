package dk.nine.demo.dto.todo;


import dk.nine.demo.model.Todo;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TodosDto {
    private String title;
    private String description;
    private UUID uuid;
    @Builder.Default
    private List<TodoDto> todoList = new ArrayList<TodoDto>(); // Initialize to avoid null issues
    private LocalDate createdAt;
    private LocalDate completedAt;


}
