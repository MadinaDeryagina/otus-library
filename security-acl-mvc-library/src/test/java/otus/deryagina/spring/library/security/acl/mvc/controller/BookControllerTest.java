package otus.deryagina.spring.library.security.acl.mvc.controller;

import org.junit.jupiter.api.BeforeEach;
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
import otus.deryagina.spring.library.security.acl.mvc.dto.AuthorDTO;
import otus.deryagina.spring.library.security.acl.mvc.dto.BookDTO;
import otus.deryagina.spring.library.security.acl.mvc.dto.GenreDTO;
import otus.deryagina.spring.library.security.acl.mvc.services.BookService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Book controller ")
@WebMvcTest
@Import({LocaleChangeInterceptor.class})
class BookControllerTest {

    private static final String TITLE = "Title";
    private static final String AUTHOR1_NAME ="Author 1" ;
    private static final String GENRE_TITLE = "Genre 1" ;
    private static final String TITLE_TWO = "TITLE 2";
    private static final int EXPECTED_BOOK_ID = 1;
    private static final String DELETE_URL = "/delete-book";
    private static final String SAVE_URL = "/save-book";
    private static final String SHOW_ALL_BOOKS_URL = "/show-all-books";


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;
    private BookDTO bookDTO1;
    private BookDTO bookDTO2;

    @BeforeEach
    void init(){
        bookDTO1 = new BookDTO();
        bookDTO1.setId(EXPECTED_BOOK_ID);
        bookDTO1.setTitle(TITLE);
        AuthorDTO authorDTO1 = new AuthorDTO(AUTHOR1_NAME);
        bookDTO1.setAuthorDTOS(Collections.singletonList(authorDTO1));
        GenreDTO genreDTO = new GenreDTO(GENRE_TITLE);
        bookDTO1.setGenreDTOS(Collections.singletonList(genreDTO));
        bookDTO2 = new BookDTO();
        bookDTO2.setTitle(TITLE_TWO);
        bookDTO2.setAuthorDTOS(Collections.singletonList(authorDTO1));
        bookDTO1.setGenreDTOS(Collections.singletonList(genreDTO));
        when(bookService.findBookById(EXPECTED_BOOK_ID)).thenReturn(Optional.of(bookDTO1));
    }
    @Test
    @WithMockUser
    @DisplayName("should return expected form and model when call get /show-all-books")
    void showAllBooks() throws Exception {
        when(bookService.findAllBooks()).thenReturn(Arrays.asList(bookDTO1,bookDTO2));
        mockMvc.perform(get(SHOW_ALL_BOOKS_URL))
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

    @DisplayName("check given get urls with no params is available for admin and user")
    @WithMockUser(roles = {"ADMIN","USER"})
    @ParameterizedTest
    @MethodSource({"generateUrlForGetWithNoParam"})
    void checkGivenGetUrlsWithNoParamsAvailableForAdminAndUser(String url) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }


    @DisplayName("check given get urls with param is available for admin")
    @WithMockUser(roles = "ADMIN")
    @ParameterizedTest
    @MethodSource({"generateUrlForGetWithParamAvailableForAdminOnly"})
    void checkGivenGetUrlsWithParamsAvailableForAdmin(String url) throws Exception {

        mockMvc.perform(get(url).param("bookId",String.valueOf(EXPECTED_BOOK_ID)))
                .andExpect(status().isOk());
    }

    @DisplayName("check given get urls with param is unavailable for user")
    @WithMockUser(roles = "USER")
    @ParameterizedTest
    @MethodSource("generateUrlForGetWithParamAvailableForAdminOnly")
    void checkGivenGetUrlsWithNoParamsUnavailableForUser(String url) throws Exception {
        mockMvc.perform(get(url).param("bookId",String.valueOf(EXPECTED_BOOK_ID)))
                .andExpect(status().isForbidden());
    }


    @DisplayName("check given post delete url is available for admin")
    @WithMockUser(roles = "ADMIN")
    @Test
    void checkGivenPostDeleteUrlAvailableForAdmin() throws Exception {
        mockMvc.perform(post(DELETE_URL)
                .param("id",String.valueOf(EXPECTED_BOOK_ID))
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(SHOW_ALL_BOOKS_URL));
    }

    @DisplayName("check given post delete url is unavailable for user")
    @WithMockUser(roles = "USER")
    @Test
    void checkGivenPostDeleteUrlUnavailableForUser() throws Exception {
        mockMvc.perform(post(DELETE_URL)
                .param("id",String.valueOf(EXPECTED_BOOK_ID))
                .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @DisplayName("check given post save url is available for user and admin")
    @WithMockUser(roles = {"USER","ADMIN"})
    @Test
    void checkGivenPostSaveUrlAvailableForGivenRoles() throws Exception {
        mockMvc.perform(post(SAVE_URL)
                .flashAttr("book", bookDTO1)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(SHOW_ALL_BOOKS_URL));
    }

    @DisplayName("check given post save url is available for user and admin")
    @Test
    void checkGivenPostSaveUrlUnavailableForNotAuthUsers() throws Exception {
        mockMvc.perform(post(SAVE_URL)
                .flashAttr("book", bookDTO1)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/show-login-form"));
    }

    private static Stream<Arguments> generateUrlForGetWithNoParam() {
        return Stream.of(
                Arguments.of(SHOW_ALL_BOOKS_URL),
                Arguments.of("/show-form-for-add-book")
        );
    }

    private static Stream<Arguments> generateUrlForGetWithParamAvailableForAdminOnly() {
        return Stream.of(
                Arguments.of("/show-form-for-update-book")
        );
    }

}