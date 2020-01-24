package otus.deryagina.spring.library.data.nosql.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "book_id")
    private Book book;

    private String text;
}
