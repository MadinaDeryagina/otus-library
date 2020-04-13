package otus.deryagina.spring.library.data.docker.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import otus.deryagina.spring.library.data.docker.dao.BookRepository;
import otus.deryagina.spring.library.data.docker.dto.AuthorDTO;
import otus.deryagina.spring.library.data.docker.dto.BookDTO;
import otus.deryagina.spring.library.data.docker.dto.GenreDTO;
import otus.deryagina.spring.library.data.docker.mapper.ModelMapperImpl;
import otus.deryagina.spring.library.data.docker.services.BookService;
import otus.deryagina.spring.library.data.docker.services.BookServiceImpl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookService")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import({BookServiceImpl.class, ModelMapperImpl.class})
@ComponentScan("otus.deryagina.spring.library.data.docker.dao")
class BookServiceImplTest {

    private static final String GIVEN_TITLE = "My book" ;
    private static final String FIRST_AUTHOR = "First author";
    private static final String SECOND_AUTHOR = "Second author" ;
    private static final String FIRST_GENRE = "Drama";
    private List<AuthorDTO> listOfAuthors;

    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;

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
        System.out.println(bookRepository.findAllByTitle(GIVEN_TITLE).toString());
        targetDTO.setId(1);
        bookService.saveOrUpdate(targetDTO);
        System.out.println(bookRepository.findAllByTitle(GIVEN_TITLE).toString());
        assertThat(bookRepository.findAllByTitle(GIVEN_TITLE).get(0).getGenres().size()).isEqualTo(1);
    }
    @DisplayName("should update author genre with less 1 genre than it was")
    @Test
    void shouldUpdateAuthorAndGenreOneLessGenreThanItWas() {
        BookDTO targetDTO= new BookDTO();
        targetDTO.setTitle(GIVEN_TITLE);
        targetDTO.setAuthorDTOS(Collections.singletonList(new AuthorDTO("Mary")));
        targetDTO.setGenreDTOS(Collections.singletonList( new GenreDTO(FIRST_GENRE)));
        System.out.println(bookRepository.findAllByTitle(GIVEN_TITLE).toString());
        targetDTO.setId(1);
        bookService.saveOrUpdate(targetDTO);
        System.out.println(bookRepository.findAllByTitle(GIVEN_TITLE).toString());
        assertThat(bookRepository.findAllByTitle(GIVEN_TITLE).get(0).getGenres().size()).isEqualTo(1);
    }
}