package dk.nine.demo.dto;

import de.huxhorn.sulky.ulid.ULID;

import java.time.LocalDate;
import java.time.Period;

public class PersonDto {
    private ULID id;
    private String firstname;
    private String lastname;
    private int age;
    private LocalDate birthDay;

    public PersonDto(String firstname, String lastname, LocalDate birthDay) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDay = birthDay;
        this.age = calculateAge(this.birthDay);
    }

    public ULID getId() {
        return id;
    }

    public void setId(ULID id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public static int calculateAge(LocalDate birthDay) {
        if( birthDay != null && birthDay.isBefore(LocalDate.now()) || birthDay.isEqual(LocalDate.now()) ) {
            throw new IllegalArgumentException("birthday must be before today");
        }
        return Period.between(birthDay, LocalDate.now()).getYears();
    }

}
