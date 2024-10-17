package dk.nine.demo.dto.person;

import dk.nine.demo.view.DtoResource;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PersonDto implements DtoResource<Long> {
    private Long id;
    private String firstname;
    private String lastname;
    private String birthday;
}
