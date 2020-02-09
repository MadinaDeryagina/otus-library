package otus.deryagina.spring.library.data.mvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import otus.deryagina.spring.library.data.mvc.dto.BookDTO;
import otus.deryagina.spring.library.data.mvc.services.BookService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/show-all-books")
    public String showAllBooks(Model model) {
        List<BookDTO> bookDTOList = bookService.findAllBooks();
        System.out.println(bookDTOList);
        model.addAttribute("books", bookDTOList);
        return "books/books";
    }

    @GetMapping("/delete-book")
    public String deleteBookById(@RequestParam("id") long id) {
        bookService.deleteBookById(id);
        return "redirect:/show-all-books";
    }

    @GetMapping("/show-form-for-add-book")
    public String showFormForAddBook(Model model) {
        BookDTO bookDTO = new BookDTO();
        model.addAttribute("book", bookDTO);
        return "books/book-form";
    }

    @GetMapping("/show-form-for-update-book")
    public String showFormForUpdateBook(@RequestParam("bookId")long bookId, Model model) {
        BookDTO bookDTO = bookService.findBookById(bookId).get();
        model.addAttribute("book", bookDTO);
        return "books/book-form";
    }

    @PostMapping("/save-book")
    public String saveBook(@ModelAttribute("book") @Validated BookDTO bookDTO) {
        bookService.saveOrUpdate(bookDTO);
        return "redirect:/show-all-books";
    }


}
