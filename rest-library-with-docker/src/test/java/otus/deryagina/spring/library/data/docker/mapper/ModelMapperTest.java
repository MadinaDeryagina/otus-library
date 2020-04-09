package otus.deryagina.spring.library.data.docker.mapper;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import otus.deryagina.spring.library.data.docker.domain.Author;
import otus.deryagina.spring.library.data.docker.domain.Book;
import otus.deryagina.spring.library.data.docker.domain.Genre;
import otus.deryagina.spring.library.data.docker.dto.BookDTO;
import otus.deryagina.spring.library.data.docker.mapper.ModelMapperImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("ModelMapper ")
@ExtendWith(SpringExtension.class)
class ModelMapperTest {

    @Configuration
    @ComponentScan(basePackageClasses = {ModelMapper.class, ModelMapperImpl.class, ModelMapperTest.class})
    public static class BookMapperSpringTestConfiguration {
    }

    @Autowired
    private ModelMapper modelMapper;

    private Book book;

    @BeforeEach
    private void init() {
        Author author1 = new Author(1, "Pushkin");
        Author author2 = new Author(2,"Ne Pushkin");
        Genre genre1 = new Genre(1, "Poetry");
        Genre genre2 = new Genre(2,"Drama");
        book = new Book(1, "Evgenyi Onegin", Arrays.asList(author1, author2),
                Arrays.asList(genre1, genre2));
    }

    @DisplayName("shouldn't be null")
    @Test
    void bookMapperIsNotNull() {
        assertThat(modelMapper).isNotNull();
    }

    @DisplayName("should have same book title in source entity and target dto")
    @Test
    void shouldHaveSameBookTitleInSourceEntityAndTargetDTO() {
        BookDTO bookDTO = modelMapper.entityToDto(book);
        if (bookDTO == null) {
            fail("bookDto is null");
        }
        assertThat(bookDTO.getTitle()).isEqualTo(book.getTitle());
    }

    @DisplayName("should have same book id in source entity and target dto")
    @Test
    void shouldHaveSameBookIdInSourceEntityAndTargetDTO() {
        BookDTO bookDTO = modelMapper.entityToDto(book);
        if (bookDTO == null) {
            fail("bookDto is null");
        }
        assertThat(bookDTO.getId()).isEqualTo(book.getId());
    }

    @DisplayName("should have not null authorsDTOS in booktDTO")
    @Test
    void shouldHaveNotNullAuthorsInBookDTO() {
        BookDTO bookDTO = modelMapper.entityToDto(book);
        if (bookDTO.getAuthorDTOS() == null || bookDTO.getAuthorDTOS().isEmpty()) {
            fail("authors in bookDTO couldn't be null or empty");
        }
    }

    @DisplayName("should have same fullNames in authorDTOS and authors in bookDTO and book")
    @Test
    void shouldHaveSameAuthorsNames() {
        BookDTO bookDTO = modelMapper.entityToDto(book);
        List<String> authorsNames = book.getAuthors().stream().map(Author::getFullName).collect(Collectors.toList());
        System.out.println("authors dto: " + bookDTO.getAuthorDTOS());
        assertThat(bookDTO.getAuthorDTOS()).extracting("fullName").containsAll(authorsNames);
    }

    @DisplayName("should have not null genreDTOS in booktDTO")
    @Test
    void shouldHaveNotNullGenresInBookDTO() {
        BookDTO bookDTO = modelMapper.entityToDto(book);
        if (bookDTO.getGenreDTOS() == null || bookDTO.getGenreDTOS().isEmpty()) {
            fail("genres in bookDTO couldn't be null or empty");
        }
    }
    @DisplayName("should have same names in genresDTOS and authors in bookDTO and book")
    @Test
    void shouldHaveSameGenresNames() {
        BookDTO bookDTO = modelMapper.entityToDto(book);
        List<String> genresNames = book.getGenres().stream().map(Genre::getName).collect(Collectors.toList());
        System.out.println("genres dto: " + bookDTO.getGenreDTOS());
        assertThat(bookDTO.getGenreDTOS()).extracting("name").containsAll(genresNames);
    }
}