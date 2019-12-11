package otus.deryagina.spring.libraryjdbc.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import otus.deryagina.spring.libraryjdbc.dao.BookDao;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BookService")
@RequiredArgsConstructor
class BookServiceImplTest {

    @MockBean
    private BookDao bookDao;

    @Test
    void addBook() {
    }

    @Test
    void findAllBooks() {
    }

    @Test
    void findBookById() {
    }

    @Test
    void findBookByTitle() {
    }
}