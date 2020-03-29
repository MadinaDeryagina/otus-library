package otus.deryagina.spring.library.data.metrics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
