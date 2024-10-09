package dk.nine.demo.controller;

import dk.nine.demo.singletons.UlidGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class HttpController {

    // Inject the UlidGenerator using Spring's dependency injection
    private final UlidGenerator ulidGenerator;
    private static final Logger logger = LoggerFactory.getLogger(HttpController.class);
    private AtomicInteger numberOfRequests = new AtomicInteger(0);
    @Autowired
    public HttpController(UlidGenerator ulidGenerator) {
        this.ulidGenerator = ulidGenerator;
    }

    /**
     * @param message
     * @param name
     * @return String response with status code 200 if both Parameters/Variables are defined
     */
    @GetMapping("/{message}")
    public String helloWorld(@PathVariable String message, @RequestParam String name) {
        // Generate a new ULID using the injected ulidGenerator
        String newUlid = ulidGenerator.generateNewUlid();

        String output =  String.format("%s %s! Your new ULID is: %s, requestNo: %s", message, name, newUlid, numberOfRequests.incrementAndGet());
        logger.debug(output);
        return output;
    }
}
