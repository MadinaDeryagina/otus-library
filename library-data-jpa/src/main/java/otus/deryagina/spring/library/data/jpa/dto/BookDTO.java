package otus.deryagina.spring.library.data.jpa.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Data
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class BookDTO {
    private long id;
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
