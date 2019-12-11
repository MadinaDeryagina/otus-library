package otus.deryagina.spring.libraryjdbc.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Data
@ToString
public class Genre {

    private final long id;
    private final String name;

}
