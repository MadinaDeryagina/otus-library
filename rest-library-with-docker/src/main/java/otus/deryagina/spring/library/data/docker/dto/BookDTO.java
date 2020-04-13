package otus.deryagina.spring.library.data.docker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("title")
    private String title;

    @JsonProperty("authors")
    private List<AuthorDTO> authorDTOS;

    @JsonProperty("genres")
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
