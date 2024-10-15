package dk.nine.demo.view;

import dk.nine.demo.dto.lomboks.todo.TodosDto;
import dk.nine.demo.model.Todos;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {

TodosDto toDto(Todos todos);

Todos toEntity(TodosDto todosDto);
}
