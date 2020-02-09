package otus.deryagina.spring.library.data.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AuthorDTO {
    @NotEmpty
    private String fullName;

    @Override
    public String toString() {
        return fullName;
    }
}
