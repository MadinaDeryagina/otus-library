package otus.deryagina.spring.library.data.nosql.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import otus.deryagina.spring.library.data.nosql.dao.CommentRepository;
import otus.deryagina.spring.library.data.nosql.domain.Book;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeDeleteEventsListener extends AbstractMongoEventListener<Book> {
    private final CommentRepository commentRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event){
        super.onAfterDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        commentRepository.deleteAllByBook_Id(id);
    }
}
