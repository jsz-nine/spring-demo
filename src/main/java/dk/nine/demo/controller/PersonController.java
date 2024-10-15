package dk.nine.demo.controller;

import dk.nine.demo.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonDto personDto) {
        try {
            PersonDto createdPerson = personService.createPerson(personDto);
            return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/people")
    @Operation(summary = "Get all persons", description = "Returns a list of persons")
    public List<PersonDto> getAllPeople() {
        return personService.getAllPeople();
    }

}
