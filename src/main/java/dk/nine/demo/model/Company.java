package dk.nine.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "internal",name = "company")
public class Company {
    @Id
    private UUID uuid; // UUID as the primary key

    private String name;

    @PrePersist
    private void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID(); // Generate a new UUID
        }
    }
}
