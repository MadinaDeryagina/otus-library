package otus.deryagina.spring.libraryjdbc.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Book {

    private long id;
    private String title;
    private List<Author> authors;
    private List<Genre> genres;
}
