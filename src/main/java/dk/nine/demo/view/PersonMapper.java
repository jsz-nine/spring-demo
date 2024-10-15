package dk.nine.demo.view;

import dk.nine.demo.dto.person.PersonDto;
import dk.nine.demo.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PersonMapper {


    public abstract PersonDto toDto(Person person);

    public abstract Person fromDto(PersonDto personDto);
}
