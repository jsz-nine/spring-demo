package dk.nine.demo.service;

import dk.nine.demo.dto.PersonDto;
import dk.nine.demo.model.Person;
import dk.nine.demo.repository.PersonRepository;
import dk.nine.demo.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final MapperUtil mapperUtil;


    @Autowired
    public PersonService(PersonRepository personRepository, MapperUtil mapperUtil) {
        this.personRepository = personRepository;
        this.mapperUtil = mapperUtil;
    }

    public List<PersonDto> getAllPeople() {
        return personRepository.findAll().stream()
                .map(mapperUtil::toDto)
                .toList();
    }

    public PersonDto getPersonById(Long id) {
        return personRepository.findById(id)
                .map(mapperUtil::toDto)
                .orElse(null);
    }

    public PersonDto createPerson(PersonDto personDto) throws ParseException {
        Person person = mapperUtil.toEntity(personDto);
        Person savedPerson = personRepository.save(person);
        return mapperUtil.toDto(savedPerson);
    }

    public PersonDto updatePerson(Long id, PersonDto updatedPersonDto) {
        Person existingPerson = personRepository.findById(id).orElse(null);
        if (existingPerson != null) {
            existingPerson.setFirstName(updatedPersonDto.firstname());
            existingPerson.setLastName(updatedPersonDto.lastname());
            existingPerson.setBirthday(LocalDate.parse(updatedPersonDto.birthday()));
            return mapperUtil.toDto(personRepository.save(existingPerson));
        }
        return null;
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

}