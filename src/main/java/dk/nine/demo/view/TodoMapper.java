package dk.nine.demo.view;

import dk.nine.demo.dto.todo.TodoDto;
import dk.nine.demo.model.Todo;
import dk.nine.demo.utils.DateMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {TodosMapper.class, DateMapper.class})
public interface TodoMapper {


    TodoMapper MAPPER = Mappers.getMapper(TodoMapper.class);


    //    TodoDto toDto(Todo todo);
    default TodoDto toDto(Todo todo) {
        return TodoDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .createdAt(todo.getCreatedAt())
                .dueDate(todo.getDueDate())
                .completed(todo.getCompleted())
                .build();
    }

// Not working - but prefered, if i could have it work!
//    @Mapping(target = "todoList", ignore = true)
////    @Mapping(target = "id", ignore = true)
//    Todo toEntity(TodoDto todoDto);

    default Todo toEntity(TodoDto todoDto) {
        return Todo.builder()
                .dueDate(todoDto.getDueDate())
                .completed(todoDto.getCompleted())
                .description(todoDto.getDescription())
                .title(todoDto.getTitle())
                .createdAt(todoDto.getCreatedAt())
                .build();
    }

}
