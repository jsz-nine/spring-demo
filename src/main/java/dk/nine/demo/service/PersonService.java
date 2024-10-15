package dk.nine.demo.service;

import dk.nine.demo.dto.person.PersonDto;
import dk.nine.demo.model.Person;
import dk.nine.demo.repository.PersonRepository;
import dk.nine.demo.view.PersonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;


    public List<PersonDto> getAllPeople() {
        return personRepository.findAll().stream()
                .map(personMapper::toDto)
                .toList();
    }

    public PersonDto getPersonById(Long id) {
        return personRepository.findById(id)
                .map(personMapper::toDto)
                .orElse(null);
    }

    public PersonDto createPerson(PersonDto personDto) throws ParseException {
        return personMapper.toDto(personRepository.save(personMapper.fromDto(personDto)));
    }

    public PersonDto updatePerson(Long id, PersonDto updatedPersonDto) {
        Optional<Person> existingPerson = personRepository.findById(id);
        Person updatedPerson = personMapper.fromDto(updatedPersonDto);
        if (existingPerson.isPresent()) {
            Person personToUpdate = existingPerson.get();
            personToUpdate.setFirstName(updatedPerson.getFirstName());
            personToUpdate.setLastName(updatedPerson.getLastName());
            personToUpdate.setBirthday(updatedPerson.getBirthday());
            return personMapper.toDto(personRepository.save(personToUpdate));
        } else {
            throw new IllegalArgumentException("Person not found with ID: " + id);
            // Or handle the not-found case differently (e.g., return null)
        }
    }


    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

}