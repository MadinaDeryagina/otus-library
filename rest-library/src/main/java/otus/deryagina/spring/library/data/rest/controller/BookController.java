package otus.deryagina.spring.library.data.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import otus.deryagina.spring.library.data.rest.dto.BookDTO;
import otus.deryagina.spring.library.data.rest.services.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> bookDTOList = bookService.findAllBooks();
        return ResponseEntity.ok(bookDTOList);
    }

    @DeleteMapping("/book")
    public ResponseEntity<Void> deleteBookById(@RequestParam("id") long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/show-form-for-add-book")
//    public String showFormForAddBook(Model model) {
//        BookDTO bookDTO = new BookDTO();
//        model.addAttribute("book", bookDTO);
//        return "books/book-form";
//    }
//
//    @GetMapping("/show-form-for-update-book")
//    public String showFormForUpdateBook(@RequestParam("bookId")long bookId, Model model) {
//        BookDTO bookDTO = bookService.findBookById(bookId).get();
//        model.addAttribute("book", bookDTO);
//        return "books/book-form";
//    }
//
//    @PostMapping("/save-book")
//    public String saveBook(@ModelAttribute("book") @Validated BookDTO bookDTO) {
//        bookService.saveOrUpdate(bookDTO);
//        return "redirect:/show-all-books";
//    }


}
