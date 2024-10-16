package dk.nine.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;


import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(schema = "internal",name = "company")
public class Company {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id; // UUID as the primary key

    private String name;

    @PrePersist
    private void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID(); // Generate a new UUID
        }
    }

}
