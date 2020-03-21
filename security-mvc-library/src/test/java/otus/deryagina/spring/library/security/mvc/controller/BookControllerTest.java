package otus.deryagina.spring.library.security.mvc.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import otus.deryagina.spring.library.security.mvc.dto.AuthorDTO;
import otus.deryagina.spring.library.security.mvc.dto.BookDTO;
import otus.deryagina.spring.library.security.mvc.dto.GenreDTO;
import otus.deryagina.spring.library.security.mvc.services.BookService;


import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Book controller ")
@WebMvcTest
@Import(LocaleChangeInterceptor.class)
class BookControllerTest {

    private static final String TITLE = "Title";
    private static final String AUTHOR1_NAME ="Author 1" ;
    private static final String GENRE_TITLE = "Genre 1" ;
    private static final String TITLE_TWO = "TITLE 2";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    @WithMockUser
    @DisplayName("should return expected form and model when call get /show-all-books")
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
        when(bookService.findAllBooks()).thenReturn(Arrays.asList(bookDTO1,bookDTO2));
        mockMvc.perform(get("/show-all-books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/books"))
                .andExpect(model().attribute("books",hasSize(2)))
                .andExpect(model().attribute("books",
                        contains(bookDTO1, bookDTO2)));
    }

    @DisplayName("check get url with no param is available for mock user")
    @WithMockUser
    @ParameterizedTest
    @MethodSource("generateUrlForGetWithNoParam")
    void checkGetUrlsAvailableUser(String url) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @DisplayName("check get url with no param is redirect without mock user")
    @ParameterizedTest
    @MethodSource("generateUrlForGetWithNoParam")
    void checkGetUrlsRedirectToLogInForm(String url) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/show-login-form"));
    }


    private static Stream<Arguments> generateUrlForGetWithNoParam() {
        return Stream.of(
                Arguments.of("/show-all-books"),
                Arguments.of("/show-form-for-add-book")
        );
    }

}