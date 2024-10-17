package dk.nine.demo.dto.todo;


import dk.nine.demo.view.DtoResource;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
public class TodoListDto implements DtoResource<UUID> {
    private String title;
    private String description;
    @Builder.Default
    @Schema(description = "Unique ID",
            example = "ad645985-3cb9-4405-874e-248e09151e48",
            required = true)
    @NotNull
    private UUID id = null;
    @Builder.Default
    private List<TaskDto> todoList = new ArrayList<TaskDto>();
    private LocalDate createdAt;
    @Builder.Default
    private LocalDate completedAt = null;
}
