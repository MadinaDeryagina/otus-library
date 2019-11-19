package otus.deryagina.spring.libraryjdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Book {

    private final long id;
    private final String title;
    private final List<Author> authors;
    private final List<Genre> genres;
}
