package dk.nine.demo.singletons;

import de.huxhorn.sulky.ulid.ULID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UlidGenerator {

    // Create a ULID instance when the Spring context initializes
    private final ULID ulid = new ULID();

    // Expose a bean to generate ULIDs
    @Bean
    public String generateUlid() {
        return ulid.nextULID();
    }

    // Method to generate a new ULID
    public String generateNewUlid() {
        return ulid.nextULID();
    }
}
