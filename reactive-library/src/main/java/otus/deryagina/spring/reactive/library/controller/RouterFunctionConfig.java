package otus.deryagina.spring.reactive.library.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import otus.deryagina.spring.reactive.library.dao.AuthorRepository;
import otus.deryagina.spring.reactive.library.dao.BookRepository;
import otus.deryagina.spring.reactive.library.domain.Author;
import otus.deryagina.spring.reactive.library.domain.Book;
import otus.deryagina.spring.reactive.library.handler.BookHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> composedRoutes(BookHandler bookHandler,BookRepository bookRepository, AuthorRepository authorRepository) {
        return route()
                .GET("/func/books", accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON).body(bookRepository.findAll(), Book.class))
                .GET("/func/books/{id}", accept(APPLICATION_JSON),
                        request -> bookRepository.findById(request.pathVariable("id"))
                                .flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromValue(book))))
                .DELETE(("/func/books/{id}"), accept(APPLICATION_JSON),
                        serverRequest -> bookRepository.deleteById(serverRequest.pathVariable("id"))
                                .flatMap(x->noContent().build()))
                .POST("/func/books",  accept(APPLICATION_JSON),
                        serverRequest -> bookHandler.createBook(serverRequest).flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromValue(book))))
                .PUT("/func/books/{id}",  accept(APPLICATION_JSON),
                        serverRequest -> bookHandler.updateBook(serverRequest).flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromValue(book))))
                .GET("/func/authors", accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON).body(authorRepository.findAll(), Author.class))
                .DELETE(("/func/authors/{id}"), accept(APPLICATION_JSON),
                        serverRequest -> bookHandler.deleteAuthorById(serverRequest)
                                .flatMap(x->noContent().build()))
                .build();
    }
}
