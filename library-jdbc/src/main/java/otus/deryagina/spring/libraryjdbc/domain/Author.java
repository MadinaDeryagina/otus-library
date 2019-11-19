package otus.deryagina.spring.libraryjdbc.domain;

import lombok.*;


@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Author {

    private final long id;
    private final String fullName;

}
