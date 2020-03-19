package otus.deryagina.spring.reactive.library.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Document(collection = "genres")
@EqualsAndHashCode(exclude = "id")
public class Genre {
    @Id
    private String id;
    @NonNull
    private String name;

}
