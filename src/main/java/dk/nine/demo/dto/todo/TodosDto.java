package dk.nine.demo.dto.todo;


import dk.nine.demo.view.DtoResource;
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
public class TodosDto implements DtoResource<UUID> {
    private String title;
    private String description;
    @Builder.Default
    private UUID id = null;
    @Builder.Default
    private List<TodoDto> todoList = new ArrayList<TodoDto>();
    private LocalDate createdAt;
    @Builder.Default
    private LocalDate completedAt = null;
}
