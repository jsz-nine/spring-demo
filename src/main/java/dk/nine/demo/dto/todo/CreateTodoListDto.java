package dk.nine.demo.dto.todo;


import lombok.*;

@Data
@AllArgsConstructor
@Getter
public class CreateTodoListDto {
    private String title;
    private String description;
}
