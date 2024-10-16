package dk.nine.demo.view;

import dk.nine.demo.dto.todo.TodoDto;
import dk.nine.demo.model.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper extends BaseMapper<Long, TodoDto, Todo> {

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


    default Todo toModel(TodoDto todoDto) {
        return Todo.builder()
                .dueDate(todoDto.getDueDate())
                .completed(todoDto.getCompleted())
                .description(todoDto.getDescription())
                .title(todoDto.getTitle())
                .createdAt(todoDto.getCreatedAt())
                .build();
    }
}
//
//    @Named("emptyListIfNull")
//    default List<TodoDto> emptyListIfNull(List<Todo> todoList) {
//        return (todoList == null) ? new ArrayList<>() : map(todoList);
//    }
//
//    @Mapping(target = "todoList", source = "todoList", qualifiedByName = "emptyListIfNull")
//    List<TodoDto> map(List<Todo> todoList);
//
//
//    @Override
//    TodoDto toDto(Todo modelResource);
//
//
//    @Override
//    Todo toModel(TodoDto dtoResource);
//
//    @Override
//    void updateFromResource(TodoDto dtoResource, @MappingTarget Todo modelResource);
//}
