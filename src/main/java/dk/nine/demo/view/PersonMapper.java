package dk.nine.demo.view;

import dk.nine.demo.dto.person.PersonDto;
import dk.nine.demo.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonDto toDto(Person person);
    Person fromDto(PersonDto personDto);
}
