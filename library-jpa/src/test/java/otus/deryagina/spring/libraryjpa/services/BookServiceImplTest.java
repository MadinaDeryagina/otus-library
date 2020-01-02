package otus.deryagina.spring.libraryjpa.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import otus.deryagina.spring.libraryjpa.dao.BookDao;
import otus.deryagina.spring.libraryjpa.dto.AuthorDTO;
import otus.deryagina.spring.libraryjpa.dto.BookDTO;
import otus.deryagina.spring.libraryjpa.dto.GenreDTO;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookService")
@ExtendWith(SpringExtension.class)
@JdbcTest
@Import({BookServiceImpl.class, ModelMapperImpl.class})
@ComponentScan("otus.deryagina.spring.libraryjdbc.dao")
class BookServiceImplTest {

    private static final String GIVEN_TITLE = "My book" ;
    private static final String FIRST_AUTHOR = "First author";
    private static final String SECOND_AUTHOR = "Second author" ;
    private static final String FIRST_GENRE = "Drama";
    private static final String SECOND_GENRE = "Prose";
    private List<AuthorDTO> listOfAuthors;

    @Autowired
    private  BookService bookService;
    @Autowired
    private BookDao bookDao;

    @BeforeEach
    void init(){
        AuthorDTO author1 = new AuthorDTO(FIRST_AUTHOR);
        AuthorDTO author2 = new AuthorDTO(SECOND_AUTHOR);
        listOfAuthors = new ArrayList<>();
        listOfAuthors.add(author1);
        listOfAuthors.add(author2);
    }

    @DisplayName("should update genre with less 1 genre than it was")
    @Test
    void shouldUpdateGenreOneLessGenreThanItWas() {
        BookDTO targetDTO= new BookDTO();
        targetDTO.setTitle(GIVEN_TITLE);
        targetDTO.setAuthorDTOS(listOfAuthors);
        targetDTO.setGenreDTOS(Collections.singletonList( new GenreDTO(FIRST_GENRE)));
        System.out.println(bookDao.findBooksByTitle(GIVEN_TITLE).toString());
        bookService.updateBook(1,targetDTO);
        System.out.println(bookDao.findBooksByTitle(GIVEN_TITLE).toString());
        assertThat(bookDao.findBooksByTitle(GIVEN_TITLE).get(0).getGenres().size()).isEqualTo(1);
    }
    @DisplayName("should update author genre with less 1 genre than it was")
    @Test
    void shouldUpdateAuthorAndGenreOneLessGenreThanItWas() {
        BookDTO targetDTO= new BookDTO();
        targetDTO.setTitle(GIVEN_TITLE);
        targetDTO.setAuthorDTOS(Collections.singletonList(new AuthorDTO("Mary")));
        targetDTO.setGenreDTOS(Collections.singletonList( new GenreDTO(FIRST_GENRE)));
        System.out.println(bookDao.findBooksByTitle(GIVEN_TITLE).toString());
        bookService.updateBook(1,targetDTO);
        System.out.println(bookDao.findBooksByTitle(GIVEN_TITLE).toString());
        assertThat(bookDao.findBooksByTitle(GIVEN_TITLE).get(0).getGenres().size()).isEqualTo(1);
    }
}