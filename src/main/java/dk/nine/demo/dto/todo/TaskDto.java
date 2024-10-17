package dk.nine.demo.dto.todo;


import dk.nine.demo.view.DtoResource;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class TaskDto implements DtoResource<Long> {
    private Long id;
    private String title;
    private String description;
    private LocalDate createdAt;
    private LocalDate dueDate;
    private Boolean completed;
}
