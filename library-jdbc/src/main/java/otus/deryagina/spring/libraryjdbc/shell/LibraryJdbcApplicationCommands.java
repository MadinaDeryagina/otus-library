package otus.deryagina.spring.libraryjdbc.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import otus.deryagina.spring.libraryjdbc.interaction.InteractionService;
import otus.deryagina.spring.libraryjdbc.services.AuthorService;
import otus.deryagina.spring.libraryjdbc.services.BookService;
import otus.deryagina.spring.libraryjdbc.services.GenreService;

@ShellComponent
@AllArgsConstructor
public class LibraryJdbcApplicationCommands {

    private final GenreService genreService;
    private final BookService bookService;
    private final AuthorService authorService;
    private final InteractionService interactionService;


    @ShellMethod(value = "show all books", key = {"sab"})
    public void showAllBooks() {
        System.out.println(bookService.findAllBooks());
    }

    @ShellMethod(value = "show book by id ", key = {"sbi"})
    public void showBookById(long id) {
        System.out.println(bookService.findBookById(id));
    }

    @ShellMethod(value = "show all genres", key = {"sag"})
    public void showAllGenres() {
        System.out.println(genreService.findAll());
    }

    @ShellMethod(value = "show all authors", key = {"saa"})
    public void showAllAuthors() {
        System.out.println(authorService.findAll());
    }

    @ShellMethod(value = "add new book", key = {"addbook", "ab"})
    public void addBook() {
        interactionService.askToAddBook();
    }
    @ShellMethod(value = "update book by id", key = {"updatebook", "ub"})
    public void updateBookById(long id) {
        interactionService.updateBookById(id);
    }

    @ShellMethod(value = "delete book by id", key = {"deletebook", "db"})
    public void deleteBookById(long id) {
        interactionService.deleteBookById(id);
    }
}
