package dk.nine.demo.view;

import dk.nine.demo.dto.todo.TaskDto;
import dk.nine.demo.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper extends BaseMapper<Long, TaskDto, Task> {

    default TaskDto toDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .createdAt(task.getCreatedAt())
                .dueDate(task.getDueDate())
                .completed(task.getCompleted())
                .build();
    }


    default Task toModel(TaskDto taskDto) {
        return Task.builder()
                .dueDate(taskDto.getDueDate())
                .completed(taskDto.getCompleted())
                .description(taskDto.getDescription())
                .title(taskDto.getTitle())
                .createdAt(taskDto.getCreatedAt())
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
