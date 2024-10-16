package dk.nine.demo.view;

import dk.nine.demo.dto.todo.TodoDto;
import dk.nine.demo.dto.todo.TodosDto;
import dk.nine.demo.model.Todo;
import dk.nine.demo.model.Todos;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {TodoMapper.class})
public interface TodosMapper extends BaseMapper<UUID, TodosDto, Todos> {
//
//    TodosMapper INSTANCE = Mappers.getMapper( TodosMapper.class);
//    TodoMapper todoMapper = Mappers.getMapper( TodoMapper.class);
//
////    @Named("emptyListIfNull")
////    default List<TodoDto> emptyListIfNull(List<Todo> todoList) {
////        return (todoList == null) ? new ArrayList<>() : map(todoList);
////    }
//
//    @Named("emptyListIfNull")
//    default List<TodoDto> emptyListIfNull(List<Todo> todoList) {
//        return (todoList == null) ? new ArrayList<TodoDto>() : map(todoList);
//    }
//
//    @Mapping(target = "listOfTodos", source = "todoList", qualifiedByName = "emptyListIfNull")
//    List<TodoDto> map(List<Todo> todoList);
//
//
//    @Override
//    TodosDto toDto(Todos modelResource);
//
//    @Override
//    Todos toModel(TodosDto dtoResource);
//
//    @Override
//    void updateFromResource(TodosDto dtoResource, @MappingTarget Todos modelResource);

    TodoMapper todoMapper = Mappers.getMapper(TodoMapper.class);

    @Override
    default TodosDto toDto(Todos todos) {
        return TodosDto.builder()
                .id(todos.getId())
                .title(todos.getTitle())
                .description(todos.getDescription())
                .todoList(todos.getTodoList().stream().map(todoMapper::toDto).toList())
                .createdAt(todos.getCreatedAt())
                .build();
    }

    @Override
    default Todos toModel(TodosDto todosDto) {
        return Todos.builder()
                .description(todosDto.getDescription())
                .title(todosDto.getTitle())
                .createdAt(todosDto.getCreatedAt())
                .todoList(todosDto.getTodoList().stream().map(todoMapper::toModel).toList())
                .id(todosDto.getId())
                .build();
    }


    //    TodoDto toDto(Todo todo);


}
