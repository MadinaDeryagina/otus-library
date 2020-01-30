package otus.deryagina.spring.library.data.nosql.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Document(collection = "authors")
public class Author {
    @Id
    private String id;
    @NonNull
    private String fullName;

}
