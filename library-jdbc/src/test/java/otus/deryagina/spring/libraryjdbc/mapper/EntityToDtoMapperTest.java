package otus.deryagina.spring.libraryjdbc.mapper;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import otus.deryagina.spring.libraryjdbc.domain.Author;
import otus.deryagina.spring.libraryjdbc.domain.Book;
import otus.deryagina.spring.libraryjdbc.domain.Genre;
import otus.deryagina.spring.libraryjdbc.dto.BookDTO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EntityToDtoMapper ")
@ExtendWith(SpringExtension.class)
class EntityToDtoMapperTest {

    @Configuration
    @ComponentScan(basePackageClasses = {EntityToDtoMapper.class, EntityToDtoMapperImpl.class, EntityToDtoMapperTest.class})
    public static class BookMapperSpringTestConfiguration {
    }

    @Autowired
    private EntityToDtoMapper entityToDtoMapper;

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
    public void bookMapperIsNotNull() {
        assertThat(entityToDtoMapper).isNotNull();
    }

    @DisplayName("should have same book title in source entity and target dto")
    @Test
    public void shouldHaveSameBookTitleInSourceEntityAndTargetDTO() {
        BookDTO bookDTO = entityToDtoMapper.entityToDto(book);
        if (bookDTO == null) {
            fail("bookDto is null");
        }
        assertThat(bookDTO.getTitle()).isEqualTo(book.getTitle());
    }

    @DisplayName("should have same book id in source entity and target dto")
    @Test
    public void shouldHaveSameBookIdInSourceEntityAndTargetDTO() {
        BookDTO bookDTO = entityToDtoMapper.entityToDto(book);
        if (bookDTO == null) {
            fail("bookDto is null");
        }
        assertThat(bookDTO.getId()).isEqualTo(book.getId());
    }

    @DisplayName("should have not null authorsDTOS in booktDTO")
    @Test
    public void shouldHaveNotNullAuthorsInBookDTO() {
        BookDTO bookDTO = entityToDtoMapper.entityToDto(book);
        if (bookDTO.getAuthorDTOS() == null || bookDTO.getAuthorDTOS().isEmpty()) {
            fail("authors in bookDTO couldn't be null or empty");
        }
    }

    @DisplayName("should have same fullNames in authorDTOS and authors in bookDTO and book")
    @Test
    public void shouldHaveSameAuthorsNames() {
        BookDTO bookDTO = entityToDtoMapper.entityToDto(book);
        List<String> authorsNames = book.getAuthors().stream().map(Author::getFullName).collect(Collectors.toList());
        System.out.println("authors dto: " + bookDTO.getAuthorDTOS());
        assertThat(bookDTO.getAuthorDTOS()).extracting("fullName").containsAll(authorsNames);
    }

    @DisplayName("should have not null genreDTOS in booktDTO")
    @Test
    public void shouldHaveNotNullGenresInBookDTO() {
        BookDTO bookDTO = entityToDtoMapper.entityToDto(book);
        if (bookDTO.getGenreDTOS() == null || bookDTO.getGenreDTOS().isEmpty()) {
            fail("genres in bookDTO couldn't be null or empty");
        }
    }
    @DisplayName("should have same names in genresDTOS and authors in bookDTO and book")
    @Test
    public void shouldHaveSameGenresNames() {
        BookDTO bookDTO = entityToDtoMapper.entityToDto(book);
        List<String> genresNames = book.getGenres().stream().map(Genre::getName).collect(Collectors.toList());
        System.out.println("genres dto: " + bookDTO.getGenreDTOS());
        assertThat(bookDTO.getGenreDTOS()).extracting("name").containsAll(genresNames);
    }
}