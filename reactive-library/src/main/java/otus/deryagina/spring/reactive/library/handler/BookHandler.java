package otus.deryagina.spring.reactive.library.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import otus.deryagina.spring.reactive.library.dao.AuthorRepository;
import otus.deryagina.spring.reactive.library.dao.BookRepository;
import otus.deryagina.spring.reactive.library.dao.GenreRepository;
import otus.deryagina.spring.reactive.library.domain.Book;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookHandler {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;


    public Mono<Book> createBook(ServerRequest request) {
        return
                request.bodyToMono(Book.class).flatMap(book -> {
                    book.setId(null);
                    saveAuthorsAndGenresForBook(book);
                    return bookRepository.save(book);
                });

    }

    private void saveAuthorsAndGenresForBook(Book book) {
        book.getAuthors().stream().filter(x -> x.getId() == null).forEach(x -> authorRepository.save(x).subscribe());
        book.getGenres().stream().filter(x -> x.getId() == null).forEach(x -> genreRepository.save(x).subscribe());
    }

    public Mono<Book> updateBook(ServerRequest request) {
        return
                request.bodyToMono(Book.class).flatMap(book -> {
                    book.setId(request.pathVariable("id"));
                    saveAuthorsAndGenresForBook(book);
                    return bookRepository.save(book);
                });
    }

    public Mono<?> deleteAuthorById(ServerRequest request) {
        log.info("start delete by author");
        String id = request.pathVariable("id");
        return authorRepository.findById(id)
                .log("findById")
                .flatMap(bookRepository::existsByAuthorsContaining).log("check books")
                .flatMap(x -> {
                    if (x) {
                        log.info("there are books");
                        return Mono.error(new InvalidObjectDeletion("There are books with this author,delete them first"));
                    } else {
                        log.info("there are no books with this author");
                        return authorRepository.deleteById(id);
                    }
                });
    }

}
