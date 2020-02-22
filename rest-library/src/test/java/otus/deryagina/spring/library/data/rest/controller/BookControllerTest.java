package otus.deryagina.spring.library.data.rest.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import otus.deryagina.spring.library.data.rest.dto.AuthorDTO;
import otus.deryagina.spring.library.data.rest.dto.BookDTO;
import otus.deryagina.spring.library.data.rest.dto.GenreDTO;
import otus.deryagina.spring.library.data.rest.services.BookService;


import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("should return expected json list of bookDTOs  when call get /show-all-books")
    void showAllBooks() throws Exception {
        BookDTO bookDTO1 = new BookDTO();
        bookDTO1.setTitle(TITLE);
        AuthorDTO authorDTO1 = new AuthorDTO(AUTHOR1_NAME);
        bookDTO1.setAuthorDTOS(Collections.singletonList(authorDTO1));
        GenreDTO genreDTO = new GenreDTO(GENRE_TITLE);
        bookDTO1.setGenreDTOS(Collections.singletonList(genreDTO));
        BookDTO bookDTO2 = new BookDTO();
        bookDTO2.setTitle(TITLE_TWO);
        bookDTO2.setAuthorDTOS(Collections.singletonList(authorDTO1));
        bookDTO1.setGenreDTOS(Collections.singletonList(genreDTO));
        when(bookService.findAllBooks()).thenReturn(Arrays.asList(bookDTO1, bookDTO2));
        mockMvc.perform(get("/show-all-books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value(bookDTO1.getTitle()))
                .andExpect(jsonPath("$[1].title").value(bookDTO2.getTitle()))
                .andDo(print());
    }
}