package dk.nine.demo.model;


import de.huxhorn.sulky.ulid.ULID;
import dk.nine.demo.singletons.UlidGenerator;

import lombok.Getter;
import lombok.Setter;

public class Person {

    private String id;
    @Getter
    @Setter
    private String name;
    @Setter
    @Getter
    private String birthday; // You can also use LocalDate if preferred

    // Constructors, getters, and setters
    public Person() {}

    public Person(String name, String birthday) {
        this.name = name;
        this.birthday = birthday;
    }



}
