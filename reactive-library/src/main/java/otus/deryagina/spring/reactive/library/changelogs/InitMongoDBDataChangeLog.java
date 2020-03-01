package otus.deryagina.spring.reactive.library.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import otus.deryagina.spring.reactive.library.domain.Author;
import otus.deryagina.spring.reactive.library.domain.Book;
import otus.deryagina.spring.reactive.library.domain.Genre;

import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Genre drama;
    private Genre prose;
    private Genre fantasy;
    private Author shakespeare;
    private Author almostShakespeare;
    private Author tolkien;

    @ChangeSet(order = "000", id = "dropDB", author = "madina", runAlways = true)
    public void dropDb(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initGenres", author = "madina", runAlways = true)
    public void initGenres(MongoTemplate template) {
        drama = new Genre("Drama");
        drama = template.save(drama);
        prose = new Genre("Prose");
        prose = template.save(prose);
        fantasy = new Genre("Fantasy");
        fantasy = template.save(fantasy);
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "madina", runAlways = true)
    public void initAuthors(MongoTemplate template) {
        shakespeare = new Author("William Shakespeare");
        shakespeare = template.save(shakespeare);
        tolkien = new Author("J. R. R. Tolkien");
        tolkien = template.save(tolkien);
        almostShakespeare = new Author("William");
        almostShakespeare = template.save(almostShakespeare);
    }

    @ChangeSet(order = "003", id = "initBooks", author = "madina", runAlways = true)
    public void initBooks(MongoTemplate template) {
        List<Author> authorsOfHamlet = new ArrayList<>();
        authorsOfHamlet.add(shakespeare);
        List<Genre> genresOfHamlet = new ArrayList<>();
        genresOfHamlet.add(drama);
        genresOfHamlet.add(prose);
        Book hamlet = new Book("Hamlet", authorsOfHamlet, genresOfHamlet);
        hamlet = template.save(hamlet);

        List<Author> authorsOfLordOdRings = new ArrayList<>();
        authorsOfLordOdRings.add(tolkien);
        List<Genre> genreOfLordOfRings = new ArrayList<>();
        genreOfLordOfRings.add(fantasy);
        Book lordOfRings = new Book("The Lord of the Rings", authorsOfLordOdRings, genreOfLordOfRings);
        lordOfRings = template.save(lordOfRings);

        List<Author> authorsOfAlmostHamlet = new ArrayList<>();
        authorsOfAlmostHamlet.add(almostShakespeare);
        Book almostHamlet = new Book("Hamlet", authorsOfAlmostHamlet, genresOfHamlet);
        almostHamlet = template.save(almostHamlet);
    }

}
