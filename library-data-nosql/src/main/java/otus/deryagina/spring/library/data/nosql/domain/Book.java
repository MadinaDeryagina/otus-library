package otus.deryagina.spring.library.data.nosql.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
//@NamedEntityGraph(name = "book-author-entity-graph",
  //      attributeNodes = {@NamedAttributeNode("authors")})
public class Book {

    @Id
    private String id;

    private String title;

    //@ManyToMany(cascade = {CascadeType.MERGE})
    //@JoinTable(name = "books_authors_correlation", joinColumns = @JoinColumn(name = "book_id"),
    //inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

//    @LazyCollection(LazyCollectionOption.FALSE)
//    @Fetch(FetchMode.SUBSELECT)
//    @ManyToMany(cascade = {CascadeType.MERGE})
//    @JoinTable(name = "books_genres_correlation", joinColumns = @JoinColumn(name = "book_id"),
//            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;
}
