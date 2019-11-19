package otus.deryagina.spring.libraryjdbc.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import otus.deryagina.spring.libraryjdbc.dao.BookDao;
import otus.deryagina.spring.libraryjdbc.dao.GenreDao;

@ShellComponent
@AllArgsConstructor
public class LibraryJdbcApplicationCommands {

    private final GenreDao genreDao;
    private final BookDao bookDao;

    @ShellMethod(value = "show all books", key = {"sab"})
    public void showAllBooks(){
        System.out.println(bookDao.findAll());
    }

    @ShellMethod(value = "show all genres", key = {"sag"})
    public void showAllGenres(){
        System.out.println(genreDao.findAll());
    }

    @ShellMethod(value = "add new book", key = {"addbook","ab"})
    public void addBook(){


    }
}
