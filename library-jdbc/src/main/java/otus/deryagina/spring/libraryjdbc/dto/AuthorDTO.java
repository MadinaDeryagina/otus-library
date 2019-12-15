package otus.deryagina.spring.libraryjdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class AuthorDTO {
    private String fullName;
}
