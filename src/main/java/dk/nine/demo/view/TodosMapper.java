package dk.nine.demo.view;

import dk.nine.demo.dto.todo.TodosDto;
import dk.nine.demo.model.Todos;
import dk.nine.demo.utils.DateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = {TodoMapper.class, DateMapper.class})
public interface TodosMapper {

    //    @Mapping(source="todoList", target="todoList")
    @Mapping(target = "createdAt" ,dateFormat = "dd-MM-yyyy")
    @Mapping(target = "completedAt" ,dateFormat = "dd-MM-yyyy")
    TodosDto toDto(Todos todos);

    //    @Mapping(source="todoList", target="todoList")
//    @Mapping(target = "Id", ignore = true)
    @Mapping(target = "createdAt" ,dateFormat = "dd-MM-yyyy")
    @Mapping(target = "completedAt" ,dateFormat = "dd-MM-yyyy")
    Todos toEntity(TodosDto todosDto);


}
