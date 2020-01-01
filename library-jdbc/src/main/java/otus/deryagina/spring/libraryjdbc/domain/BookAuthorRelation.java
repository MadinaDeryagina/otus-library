package otus.deryagina.spring.libraryjdbc.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BookAuthorRelation {
    private final long bookId;
    private final long authorId;
}
