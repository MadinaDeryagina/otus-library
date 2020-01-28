package otus.deryagina.spring.library.data.nosql.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    @NonNull private String title;

    @Field("authors")
    @NonNull private List<Author> authors;

    @Field("genres")
    @NonNull private List<Genre> genres;
}
