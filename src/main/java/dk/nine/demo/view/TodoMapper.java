package dk.nine.demo.view;

import dk.nine.demo.dto.todo.TodoDto;
import dk.nine.demo.model.Todo;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper extends BaseMapper<Long, TodoDto, Todo> {




    TodoMapper INSTANCE = Mappers.getMapper( TodoMapper.class);

    @Override
    TodoDto toDto(Todo modelResource);


    @Override
    Todo toModel(TodoDto dtoResource);

    @Override
    void updateFromResource(TodoDto dtoResource, @MappingTarget Todo modelResource);
}
