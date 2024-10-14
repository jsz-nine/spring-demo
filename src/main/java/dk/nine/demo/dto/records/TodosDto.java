package dk.nine.demo.dto.records;

import dk.nine.demo.dto.lomboks.TodoDto;
import lombok.Builder;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@Builder
public record TodosDto(UUID uuid, String title, String description, ArrayList<TodoDto> todoDtos, LocalDate createdAt,
                       LocalDate completedAt) {

    public static class TodosDtoBuilder {
        public TodosDtoBuilder() {
        }
    }

    public TodosDto(UUID uuid, String title, String description, ArrayList<TodoDto> todoDtos, LocalDate createdAt, LocalDate completedAt) {
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.todoDtos = todoDtos;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
    }

    public TodosDto() {
        this(null, "", "", null, null, null);
    }

    public TodosDto(String title, String description) {
        this(UUID.randomUUID(), title, description, new ArrayList<>(), LocalDate.now(), null);
    }

}

