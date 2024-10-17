package dk.nine.demo.dto.person;

import dk.nine.demo.view.DtoResource;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PersonDto implements DtoResource<Long> {
    @Schema(description = "Incremental ID",
            example = "123",
            required = true)
    @NotNull
    private Long id;
    private String firstname;
    private String lastname;
    private String birthday;
}
