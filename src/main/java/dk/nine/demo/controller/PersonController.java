package dk.nine.demo.controller;

import dk.nine.demo.dto.person.PersonDto;
import dk.nine.demo.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
public class PersonController {

    @Autowired
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person/create")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PersonDto> createPerson(@Valid @RequestBody PersonDto personDto) {
        try {
            PersonDto createdPerson = personService.createPerson(personDto);
            return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/person/search/{query}")
    public Set<PersonDto> searchForPersons(@PathVariable String query) {
        if (query.length() <= 2) {
            return Collections.emptySet();
        }
        log.debug("searched for {}", query);
        Set<PersonDto> personFoundList = personService.findPersonsByName(query);

        return personFoundList;

    }


    @GetMapping("/people")
    @Operation(summary = "Get all persons", description = "Returns a list of persons")
    public List<PersonDto> getAllPeople() {
        return personService.getAllPeople();
    }

}
