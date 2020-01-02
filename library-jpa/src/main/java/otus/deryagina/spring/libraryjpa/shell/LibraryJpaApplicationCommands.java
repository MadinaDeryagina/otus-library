package otus.deryagina.spring.libraryjpa.shell;

import lombok.AllArgsConstructor;


@ShellComponent
@AllArgsConstructor
public class LibraryJpaApplicationCommands {

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
