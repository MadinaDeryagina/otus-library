package otus.deryagina.spring.library.data.docker.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import otus.deryagina.spring.library.data.docker.dto.BookDTO;
import otus.deryagina.spring.library.data.docker.services.BookService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> bookDTOList = bookService.findAllBooks();
        return ResponseEntity.ok(bookDTOList);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") long id){
        return ResponseEntity.of(bookService.findBookById(id));
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable("id") long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/book")
    public ResponseEntity<Void> saveBook(UriComponentsBuilder builder, @RequestBody @Validated BookDTO bookDTO) {
        log.info("DTO: " + bookDTO);
        bookDTO.setId(0);
        bookDTO = bookService.saveOrUpdate(bookDTO);
        UriComponents uriComponents =
                builder.path("/book/{id}").buildAndExpand(bookDTO.getId());
        return  ResponseEntity.created(uriComponents.toUri()).build();
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") long id, @RequestBody @Validated BookDTO bookDTO){
        bookDTO.setId(id);
        return ResponseEntity.ok(bookService.saveOrUpdate(bookDTO));
    }

}
