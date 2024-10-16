package dk.nine.demo.view;

import dk.nine.demo.dto.todo.TodoDto;
import dk.nine.demo.dto.todo.TodosDto;
import dk.nine.demo.model.Todo;
import dk.nine.demo.model.Todos;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {TodoMapper.class})
public interface TodosMapper extends BaseMapper<UUID, TodosDto, Todos> {


    @Named("emptyListIfNull")
    default List<TodoDto> emptyListIfNull(List<Todo> todoList) {
        return (todoList == null) ? new ArrayList<>() : map(todoList);
    }
    @Mapping(target = "todoList", source = "todoList", qualifiedByName = "emptyListIfNull")
    List<TodoDto> map(List<Todo> todoList);



    @Override
    TodosDto toDto(Todos modelResource);

    @Override
    Todos toModel(TodosDto dtoResource);

    @Override
    void updateFromResource(TodosDto dtoResource, @MappingTarget Todos modelResource);
}
