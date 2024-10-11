package dk.nine.demo.utils;

import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {
    private final DateTimeFormatter formatter;

    public LocalDateFormatter() {
        this.formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    @Override
    @NonNull
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return LocalDate.parse(text, formatter);
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return formatter.format(object);
    }
}
