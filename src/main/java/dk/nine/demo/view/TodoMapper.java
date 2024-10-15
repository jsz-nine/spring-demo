package dk.nine.demo.view;

import dk.nine.demo.dto.todo.TodoDto;
import dk.nine.demo.model.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    TodoDto toDto(Todo todo);

    Todo toEntity(TodoDto todoDto);
}
