package otus.deryagina.spring.library.data.docker.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import otus.deryagina.spring.library.data.docker.dto.AuthorDTO;
import otus.deryagina.spring.library.data.docker.dto.BookDTO;
import otus.deryagina.spring.library.data.docker.dto.GenreDTO;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("BookDTO")
class BookDTOTest {

    private static final String TITLE = "title";
    private static final String AUTHOR1_NAME = "author1";
    private static final String GENRE_TITLE = "genre";

    @DisplayName("must be equal with different author dto by same author name")
    @Test
    void equals1() {
        BookDTO bookDTO1 = new BookDTO();
        bookDTO1.setTitle(TITLE);
        AuthorDTO authorDTO1 = new AuthorDTO(AUTHOR1_NAME);
        bookDTO1.setAuthorDTOS(Collections.singletonList(authorDTO1));
        GenreDTO genreDTO = new GenreDTO(GENRE_TITLE);
        bookDTO1.setGenreDTOS(Collections.singletonList(genreDTO));
        BookDTO bookDTO2 = new BookDTO();
        AuthorDTO authorDTO2 = new AuthorDTO(AUTHOR1_NAME);
        bookDTO2.setTitle(TITLE);
        bookDTO2.setAuthorDTOS(Collections.singletonList(authorDTO2));
        bookDTO2.setGenreDTOS(Collections.singletonList(genreDTO));
        assertThat(bookDTO1.equals(bookDTO2)).isEqualTo(true);

    }
}