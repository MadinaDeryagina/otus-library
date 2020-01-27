package otus.deryagina.spring.library.data.nosql.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CommentDTO {
    private String bookId;
    private String text;

}
