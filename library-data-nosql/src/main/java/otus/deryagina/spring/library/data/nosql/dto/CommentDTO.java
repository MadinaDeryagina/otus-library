package otus.deryagina.spring.library.data.nosql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CommentDTO {
    private String bookId;
    private String text;

    @Override
    public String toString() {
        return text;
    }
}
