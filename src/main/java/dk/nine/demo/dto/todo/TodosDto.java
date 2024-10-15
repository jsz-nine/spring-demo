package dk.nine.demo.dto.todo;


import lombok.*;

import java.time.LocalDate;
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
    private List<TodoDto> todoList;
    private LocalDate createdAt;
    private LocalDate completedAt;
}
