package dk.nine.demo.controller;

import dk.nine.demo.dto.PersonDto;
import dk.nine.demo.exception.CustomExceptions;
import dk.nine.demo.model.Person;
import dk.nine.demo.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
public class HttpController {

    @Autowired
    private final PersonService personService;

    @Autowired
    public HttpController(PersonService personService) {
        this.personService = personService;
    }

    private final AtomicInteger numberOfRequests = new AtomicInteger(0);


    /**
     * @param message
     * @param name
     * @return String response with status code 200 if botEr h Parameters/Variables are defined
     */
    @GetMapping("/hello/{message}")
    public String helloWorld(@PathVariable String message, @RequestParam String name) {
        numberOfRequests.incrementAndGet();

        String output = String.format("%s \n %s!, requestNo: \n %s", message, name, numberOfRequests.get());
        switch (numberOfRequests.get() % 10) {
            case 1 ->
                    throw new CustomExceptions.RequestNumberOneException(String.format("This was a first request \n %s", output));
            case 2 ->
                    throw new CustomExceptions.RequestNumberTwoException(String.format("This was a second request \n %s", output));
            case 3 ->
                    throw new CustomExceptions.RequestNumberThreeException(String.format("This was a third request \n %s", output));
            case 4 ->
                    throw new CustomExceptions.RequestNumberFourException(String.format("This was a fourth request \n %s", output));
            case 5 ->
                    throw new CustomExceptions.RequestNumberFiveException(String.format("This was a fifth request \n %s", output));
            case 6 ->
                    throw new CustomExceptions.RequestNumberSixException(String.format("This was a sixth request \n %s", output));
            case 7 ->
                    throw new CustomExceptions.RequestNumberSevenException(String.format("This was a seventh request \n %s", output));
            case 8 ->
                    throw new CustomExceptions.RequestNumberEightException(String.format("This was an eighth request \n %s", output));
            case 9 ->
                    throw new CustomExceptions.RequestNumberNineException(String.format("This was a ninth request \n %s", output));
            default -> {
            }
        }
        ;

        log.debug(output);
        return output;
    }


    @PostMapping("/person/create")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonDto personDto) {
        try {
            PersonDto createdPerson = personService.createPerson(personDto);
            return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle potential exceptions like database errors, validation errors, etc.
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/people")
    @Operation(summary = "Get all persons", description = "Returns a list of persons")
    public List<PersonDto> getAllPeople() {
        return personService.getAllPeople();
    }

}
