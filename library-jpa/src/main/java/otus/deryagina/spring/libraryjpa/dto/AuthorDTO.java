package otus.deryagina.spring.libraryjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AuthorDTO {
    private String fullName;

    @Override
    public String toString() {
        return fullName;
    }
}