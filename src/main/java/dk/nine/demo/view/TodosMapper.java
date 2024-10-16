package dk.nine.demo.view;

import dk.nine.demo.dto.todo.TodosDto;
import dk.nine.demo.model.Todos;
import dk.nine.demo.utils.DateMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {TodoMapper.class, DateMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TodosMapper extends BaseMapper<UUID,  TodosDto, Todos> {
    @Override
    TodosDto toDto(Todos persistentItem);

    @Override
    Todos toEntity(TodosDto resource) ;

    @Override
    void updateFromResource(TodosDto resource,@MappingTarget Todos persistentItem);
}
