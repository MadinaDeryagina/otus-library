package otus.deryagina.spring.libraryjdbc.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Data
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class BookDTO {
    private long id;
    private String title;
    private List<AuthorDTO> authorDTOS;
    private List<GenreDTO> genreDTOS;
}
