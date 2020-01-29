package otus.deryagina.spring.library.data.mvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import otus.deryagina.spring.library.data.mvc.services.BookService;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

}
