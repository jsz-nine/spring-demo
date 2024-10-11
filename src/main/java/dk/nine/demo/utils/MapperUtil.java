package dk.nine.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.nine.demo.dto.records.PersonDto;
import dk.nine.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;


// basicly just to see how manual json parsing and serialization works.
@Component
public class MapperUtil {

    private final ObjectMapper objectMapper;
    private final LocalDateFormatter dateFormatter;

    @Autowired
    public MapperUtil(ObjectMapper objectMapper, LocalDateFormatter dateFormatter) {
        this.objectMapper = objectMapper;
        this.dateFormatter = dateFormatter;
    }

    public PersonDto toDto(Person person) {
        return new PersonDto(person.getId(), person.getFirstName(), person.getLastName(), person.getBirthday().toString());
    }

    public Person toEntity(PersonDto personDto) throws ParseException {
        return new Person(personDto.id(), personDto.firstname(), personDto.lastname(), dateFormatter.parse(personDto.birthday(), Locale.getDefault()));
    }

    public String serializeToJson(PersonDto personDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDto);
    }

    public PersonDto deserializeFromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, PersonDto.class);
    }

}
