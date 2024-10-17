package dk.nine.demo.service;

import dk.nine.demo.dto.person.PersonDto;
import dk.nine.demo.model.Person;
import dk.nine.demo.repository.PersonRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    private ModelMapper modelMapper;


    public List<PersonDto> getAllPeople() {
        return personRepository.findAll().stream()
                .map(person -> modelMapper.map(person, PersonDto.class))
                .toList();
    }

    public PersonDto getPersonById(Long id) {
        return personRepository.findById(id)
                .map(person -> modelMapper.map(person, PersonDto.class))
                .orElse(null);
    }

    public Set<PersonDto> findPersonsByName(String query) {
        return personRepository.findPersonByQuery(query)
                .stream().map(person -> modelMapper.map(person,PersonDto.class)).collect(Collectors.toSet());
    }

    public PersonDto createPerson(PersonDto personDto) throws ParseException {
        return modelMapper.map(personRepository.save(modelMapper.map(personDto, Person.class)), PersonDto.class);
    }

    public PersonDto updatePerson(Long id, PersonDto updatedPersonDto) {
        Optional<Person> existingPerson = personRepository.findById(id);
        Person updatedPerson = modelMapper.map(updatedPersonDto, Person.class);
        if (existingPerson.isPresent()) {
            Person personToUpdate = existingPerson.get();
            personToUpdate.setFirstname(updatedPerson.getFirstname());
            personToUpdate.setLastname(updatedPerson.getLastname());
            personToUpdate.setBirthday(updatedPerson.getBirthday());
            return modelMapper.map(personRepository.save(personToUpdate), PersonDto.class);
        } else {
            throw new IllegalArgumentException("Person not found with ID: " + id);
            // Or handle the not-found case differently (e.g., return null)
        }
    }


    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

}