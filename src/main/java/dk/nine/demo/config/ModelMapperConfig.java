package dk.nine.demo.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        Converter<LocalDate, String> localDateToStringConverter = new Converter<LocalDate, String>() {

            @Override
            public String convert(MappingContext<LocalDate, String> context) {
                LocalDate sourceDate = context.getSource();
                if (sourceDate != null) {
                    // Format the date as needed
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Example format
                    return sourceDate.format(formatter);
                } else {
                    return null; // Or handle null dates as you prefer
                }
            }
        };

        // Register the converter for LocalDate to String mapping
        mapper.createTypeMap(LocalDate.class, String.class).setConverter(localDateToStringConverter);

        return mapper;

    }
}