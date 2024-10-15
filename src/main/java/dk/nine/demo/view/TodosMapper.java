package dk.nine.demo.view;

import dk.nine.demo.dto.todo.TodosDto;
import dk.nine.demo.model.Todos;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TodoMapper.class})
public interface TodosMapper {

    TodosDto toDto(Todos todos);

    Todos toEntity(TodosDto todosDto);


}
