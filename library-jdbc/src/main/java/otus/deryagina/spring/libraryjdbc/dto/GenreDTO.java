package otus.deryagina.spring.libraryjdbc.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GenreDTO {
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
