package otus.deryagina.spring.reactive.library.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import otus.deryagina.spring.reactive.library.dao.AuthorRepository;
import otus.deryagina.spring.reactive.library.dao.BookRepository;
import otus.deryagina.spring.reactive.library.dao.GenreRepository;
import otus.deryagina.spring.reactive.library.domain.Author;
import otus.deryagina.spring.reactive.library.domain.Book;
import otus.deryagina.spring.reactive.library.domain.Genre;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookHandler {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;


    public Mono<Book> saveBook(ServerRequest request,boolean asNew) {
     return  request.bodyToMono(Book.class).flatMap(
             book -> {
                 if(!asNew) {
                     String id = request.pathVariable("id");
                     book.setId(id);
                 }else {
                     book.setId(null);
                 }
                 Mono<List<Author>> monoAuthors =
                         Flux.fromIterable(book.getAuthors())
                                 .flatMap(authorRepository::save)
                                 .collectList();

                 Mono<List<Genre>> monoGenres = Flux.fromIterable(book.getGenres())
                         .flatMap(genreRepository::save).collectList();
                 return Mono.zip(monoAuthors,monoGenres, (authors,genres)->{
                     book.setAuthors(authors);
                     book.setGenres(genres);
                        return book;
                 });
             }).flatMap(bookRepository::save);
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
