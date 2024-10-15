package dk.nine.demo.view;

import dk.nine.demo.dto.todo.TodoDto;
import dk.nine.demo.model.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",  uses = {TodosMapper.class})
public interface TodoMapper {
    TodoDto toDto(Todo todo);

    @Mapping(target = "todoList", ignore = true)
    @Mapping(target = "id", ignore = true)
    Todo toEntity(TodoDto todoDto);
}
