package dk.nine.demo.view;

import dk.nine.demo.dto.todo.TodoDto;
import dk.nine.demo.dto.todo.TodosDto;
import dk.nine.demo.model.Todo;
import dk.nine.demo.model.Todos;
import dk.nine.demo.utils.DateMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Mapper(componentModel = "spring", uses = {TodoMapper.class, DateMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TodosMapper {

    TodosMapper MAPPER = Mappers.getMapper(TodosMapper.class);
    TodoMapper todoMapper = Mappers.getMapper(TodoMapper.class);

    default TodosDto toDto(Todos todos) {
        return TodosDto.builder()
                .uuid(todos.getUuid())
                .title(todos.getTitle())
                .description(todos.getDescription())
                .createdAt(todos.getCreatedAt())
                .todoList(todos.getTodoList().stream().map(todoMapper::toDto).toList())
                .build();
    }


//    Todos toEntity(TodosDto todosDto);

    default Todos toEntity(TodosDto todosDto) {
        return Todos.builder()
                .uuid(todosDto.getUuid())
                .completedAt(todosDto.getCompletedAt())
                .createdAt(todosDto.getCreatedAt())
                .description(todosDto.getDescription())
                .title(todosDto.getTitle())
                .build();
    }

}
