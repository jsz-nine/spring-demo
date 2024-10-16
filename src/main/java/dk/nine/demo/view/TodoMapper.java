package dk.nine.demo.view;

import dk.nine.demo.dto.todo.TodoDto;
import dk.nine.demo.model.Todo;
import dk.nine.demo.utils.DateMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {TodosMapper.class, DateMapper.class})
public interface TodoMapper extends BaseMapper<Long, TodoDto, Todo> {


    @Override
    TodoDto toDto(Todo persistentItem);

    @Mapping(target = "todoList", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Override
    Todo toEntity(TodoDto resource);


    @Override
    void updateFromResource(TodoDto resource, @MappingTarget Todo persistentItem);
}
