package dk.nine.demo.dto.lomboks;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CompanyDto {
    private UUID id;
    private String name;
}