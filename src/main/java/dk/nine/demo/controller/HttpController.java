package dk.nine.demo.controller;

import dk.nine.demo.exception.CustomExceptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
public class HttpController {

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
        log.debug(output);
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


        return output;
    }
}
