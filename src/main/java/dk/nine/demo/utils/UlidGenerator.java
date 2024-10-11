package dk.nine.demo.utils;

import de.huxhorn.sulky.ulid.ULID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class UlidGenerator {

    @Bean
    @Scope("singleton")
    public ULID generate() {
        return new ULID();
    }
}

