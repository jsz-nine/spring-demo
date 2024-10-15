package dk.nine.demo.view;

import dk.nine.demo.dto.todo.TodoDto;
import dk.nine.demo.model.Todo;
import dk.nine.demo.utils.DateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring",  uses = {TodosMapper.class, DateMapper.class})
public interface TodoMapper {
    TodoDto toDto(Todo todo);

    @Mapping(target = "todoList", ignore = true)
    @Mapping(target = "id", ignore = true)
    Todo toEntity(TodoDto todoDto);
}
