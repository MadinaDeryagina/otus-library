package otus.deryagina.spring.library.security.acl.mvc.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@Data
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class BookDTO {
    private long id;
    @NotBlank
    private String title;
    private List<AuthorDTO> authorDTOS;
    private List<GenreDTO> genreDTOS;

    @Override
    public String toString() {
        return "Book: " +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authorDTOS +
                ", genres=" + genreDTOS +
                '}';
    }
}
