package otus.deryagina.spring.library.data.mvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import otus.deryagina.spring.library.data.mvc.dto.BookDTO;
import otus.deryagina.spring.library.data.mvc.exceptions.BookNotFoundException;
import otus.deryagina.spring.library.data.mvc.services.BookService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<BookDTO> bookDTOList = bookService.findAllBooks();
        System.out.println(bookDTOList);
        model.addAttribute("books", bookDTOList);
        return "books/books";
    }

//    @GetMapping("/book")
//    public String showBookFullInfo(@RequestParam("id") long id, Model model) {
//        BookDTO bookDTO = bookService.findBookById(id).orElseThrow(BookNotFoundException::new);
//        model.addAttribute("book", bookDTO);
//        return "full-book-info";
//    }

    @GetMapping("/delete")
    public String deleteBookById(@RequestParam("id") long id) {
        bookService.deleteBookById(id);
        return "redirect:/books";
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
    public String saveBook(@ModelAttribute("book") BookDTO bookDTO) {
        //если bookId равнен 0, то проверка на наличие такой же книге в библиотеке
        //TODO: если такая же книга есть а библиотеке, то редирект на ее страницу с апдейтом
        bookService.saveOrUpdate(bookDTO);
        return "redirect:/books";
    }


}
