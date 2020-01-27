package otus.deryagina.spring.library.data.nosql.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "books")
//@NamedEntityGraph(name = "book-author-entity-graph",
  //      attributeNodes = {@NamedAttributeNode("authors")})
public class Book {

    @Id
    private String id;

    @NonNull private String title;

    //@ManyToMany(cascade = {CascadeType.MERGE})
    //@JoinTable(name = "books_authors_correlation", joinColumns = @JoinColumn(name = "book_id"),
    //inverseJoinColumns = @JoinColumn(name = "author_id"))
    @Field("authors")
    @NonNull private List<Author> authors;

//    @LazyCollection(LazyCollectionOption.FALSE)
//    @Fetch(FetchMode.SUBSELECT)
//    @ManyToMany(cascade = {CascadeType.MERGE})
//    @JoinTable(name = "books_genres_correlation", joinColumns = @JoinColumn(name = "book_id"),
//            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @Field("genres")
    @NonNull private List<Genre> genres;
}
