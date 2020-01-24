package otus.deryagina.spring.libraryjpa.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CommentDTO {
    private long bookId;
    private String text;

}
