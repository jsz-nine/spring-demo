package dk.nine.demo.view;

import dk.nine.demo.dto.todo.TodoListDto;
import dk.nine.demo.model.TodoList;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface TodoListMapper extends BaseMapper<UUID, TodoListDto, TodoList> {
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

    TaskMapper TASK_MAPPER = Mappers.getMapper(TaskMapper.class);

    @Override
    default TodoListDto toDto(TodoList todoList) {
        return TodoListDto.builder()
                .id(todoList.getId())
                .title(todoList.getTitle())
                .description(todoList.getDescription())
                .todoList(todoList.getTaskList().stream().map(TASK_MAPPER::toDto).toList())
                .createdAt(todoList.getCreatedAt())
                .build();
    }

    @Override
    default TodoList toModel(TodoListDto todoListDto) {
        return TodoList.builder()
                .description(todoListDto.getDescription())
                .title(todoListDto.getTitle())
                .createdAt(todoListDto.getCreatedAt())
                .taskList(todoListDto.getTodoList().stream().map(TASK_MAPPER::toModel).toList())
                .id(todoListDto.getId())
                .build();
    }


    //    TodoDto toDto(Todo todo);


}
