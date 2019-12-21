package otus.deryagina.spring.libraryjdbc.domain;

import lombok.*;


@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Author {

    private long id;
    private String fullName;

}
