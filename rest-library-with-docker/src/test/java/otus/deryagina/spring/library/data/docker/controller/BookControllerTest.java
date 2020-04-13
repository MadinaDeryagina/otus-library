package otus.deryagina.spring.library.data.docker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import otus.deryagina.spring.library.data.docker.dto.AuthorDTO;
import otus.deryagina.spring.library.data.docker.dto.BookDTO;
import otus.deryagina.spring.library.data.docker.dto.GenreDTO;
import otus.deryagina.spring.library.data.docker.services.BookService;


import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Book controller ")
@WebMvcTest
@Import(LocaleChangeInterceptor.class)
class BookControllerTest {

    private static final String TITLE = "Title";
    private static final String AUTHOR1_NAME = "Author 1";
    private static final String GENRE_TITLE = "Genre 1";
    private static final String TITLE_TWO = "TITLE 2";
    private static final long EXPECTED_ID = 22;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    private BookDTO bookDTO1;

    private BookDTO bookDTO2;

    @BeforeEach
    void init() {
        bookDTO1 = new BookDTO();
        bookDTO1.setTitle(TITLE);
        AuthorDTO authorDTO1 = new AuthorDTO(AUTHOR1_NAME);
        bookDTO1.setAuthorDTOS(Collections.singletonList(authorDTO1));
        GenreDTO genreDTO = new GenreDTO(GENRE_TITLE);
        bookDTO1.setGenreDTOS(Collections.singletonList(genreDTO));
        bookDTO2 = new BookDTO();
        bookDTO2.setTitle(TITLE_TWO);
        bookDTO2.setAuthorDTOS(Collections.singletonList(authorDTO1));
        bookDTO2.setGenreDTOS(Collections.singletonList(genreDTO));
    }

    @Test
    @DisplayName("should return expected json list of bookDTOs  when call get /books")
    void showAllBooks() throws Exception {

        when(bookService.findAllBooks()).thenReturn(Arrays.asList(bookDTO1, bookDTO2));
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value(bookDTO1.getTitle()))
                .andExpect(jsonPath("$[1].title").value(bookDTO2.getTitle()))
                .andDo(print());
    }

    @Test
    @DisplayName("should return created and uri in header when post")
    void shouldPostCorrectly() throws Exception {
        BookDTO bookDTOForService = new BookDTO();
        bookDTOForService.setId(EXPECTED_ID);
        bookDTOForService.setTitle(bookDTO1.getTitle());
        bookDTOForService.setAuthorDTOS(bookDTO1.getAuthorDTOS());
        bookDTOForService.setGenreDTOS(bookDTO1.getGenreDTOS());
        when(bookService.saveOrUpdate(bookDTO1)).thenReturn(bookDTOForService);
        mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(bookDTO1)))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrl("http://localhost/book/"+EXPECTED_ID))
                .andDo(print());
    }


}