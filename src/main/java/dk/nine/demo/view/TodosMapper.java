package dk.nine.demo.view;

import dk.nine.demo.dto.todo.TodosDto;
import dk.nine.demo.model.Todos;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TodoMapper.class})
public interface TodosMapper {


//    @Mapping(source="todoList", target="todoList")
    TodosDto toDto(Todos todos);
//    @Mapping(source="todoList", target="todoList")
//    @Mapping(target = "Id", ignore = true)
    Todos toEntity(TodosDto todosDto);


}
