package dk.nine.demo.dto.todo;


import com.fasterxml.jackson.annotation.JsonProperty;
import dk.nine.demo.view.BaseResource;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class TodoDto implements BaseResource<Long> {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String title;
    private String description;
    private LocalDate createdAt;
    private LocalDate dueDate;
    private Boolean completed;
}
