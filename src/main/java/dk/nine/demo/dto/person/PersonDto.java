package dk.nine.demo.dto.person;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PersonDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String birthday;
}
