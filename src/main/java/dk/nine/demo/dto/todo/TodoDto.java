package dk.nine.demo.dto.todo;


import com.fasterxml.jackson.annotation.JsonProperty;
import dk.nine.demo.view.DtoResource;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class TodoDto implements DtoResource<Long> {
    private Long id;
    private String title;
    private String description;
    private LocalDate createdAt;
    private LocalDate dueDate;
    private Boolean completed;
}
