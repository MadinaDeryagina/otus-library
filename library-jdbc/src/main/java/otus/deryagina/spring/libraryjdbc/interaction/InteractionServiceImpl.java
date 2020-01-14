package otus.deryagina.spring.libraryjdbc.interaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import otus.deryagina.spring.libraryjdbc.dto.AuthorDTO;
import otus.deryagina.spring.libraryjdbc.dto.BookDTO;
import otus.deryagina.spring.libraryjdbc.dto.GenreDTO;
import otus.deryagina.spring.libraryjdbc.iostreams.IOStreamsProvider;
import otus.deryagina.spring.libraryjdbc.localizer.LocalizationService;
import otus.deryagina.spring.libraryjdbc.services.BookService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InteractionServiceImpl implements InteractionService {

    private final IOStreamsProvider ioStreamsProvider;
    private final LocalizationService localizationService;
    private final BookService bookService;

    @Override
    public void askToAddBook() {
        BookDTO bookDTO = createBookDTOByInputData();
        if (bookDTO == null) {
            return;
        }
        List<BookDTO> booksWithSameTitle = bookService.findBooksByTitle(bookDTO.getTitle());
        if (booksWithSameTitle.isEmpty()) {
            Long newBookId = bookService.addAsNewBook(bookDTO);
            ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("add.book.success", newBookId));
            return;
        }
        List<BookDTO> exactlySameBook = booksWithSameTitle.stream().filter(x -> x.equals(bookDTO)).collect(Collectors.toList());
        if (!exactlySameBook.isEmpty()) {
            ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("same.book.in.lib", exactlySameBook));
            return;
        }
        chooseToUpdateOrAddAsNew(bookDTO, booksWithSameTitle);
    }

    private void chooseToUpdateOrAddAsNew(BookDTO bookDTO, List<BookDTO> booksWithSameTitle) {
        ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("found.books.with.same.title", booksWithSameTitle));
        String yesOrNo = ioStreamsProvider.readData();
        if (yesOrNo.equalsIgnoreCase(localizationService.getLocalizedMessage("yes"))) {
            bookService.addAsNewBook(bookDTO);
        } else if (yesOrNo.equalsIgnoreCase(localizationService.getLocalizedMessage("no"))){
            //which book do you want yo update
            ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("which.to.update"));
            String bookIdToUpdateString = ioStreamsProvider.readData();
            Long bookIdToUpdate = Long.parseLong(bookIdToUpdateString);
            boolean isValidId=false;
            for (BookDTO currentBook:booksWithSameTitle) {
                if(currentBook.getId()== bookIdToUpdate){
                    isValidId=true;
                }
            }
            if(!isValidId){
                ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("invalid.input.id", bookIdToUpdate));
                return;
            }
            if(bookService.updateBook(bookIdToUpdate, bookDTO)){
                ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("update.success"));
            }else {
                ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("update.fail"));
            }
        }
    }

    @Override
    public void updateBookById(Long id) {
        if(bookService.findBookById(id)==null){
            ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("invalid.input.id", id));
            return;
        }
        BookDTO bookDTO = createBookDTOByInputData();
        if (bookDTO == null) {
            return;
        }
        bookService.updateBook(id,bookDTO);

    }

    @Override
    public void deleteBookById(Long id) {
        if(bookService.findBookById(id)==null){
            ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("invalid.input.id", id));
            return;
        }
        bookService.deleteBookById(id);
    }


    private BookDTO createBookDTOByInputData() {
        BookDTO bookDTO = new BookDTO();
        ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("insert.book.param", localizationService.getLocalizedMessage("book.title")));
        String title = ioStreamsProvider.readData();
        if (isInputValid(title)) {
            bookDTO.setTitle(title);
        } else {
            ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("param.not.null", localizationService.getLocalizedMessage("book.title")));
            return null;
        }
        ioStreamsProvider.printInfo(localizationService.getLocalizedMessageByMultipleKeys("insert.book.param","book.authors") + localizationService.getLocalizedMessage("use.comma"));
        if (askAndAddAuthors(bookDTO)) return null;
        if (askAndAddGenres(bookDTO)) return null;
        return bookDTO;
    }

    private boolean askAndAddGenres(BookDTO bookDTO) {
        ioStreamsProvider.printInfo(localizationService.getLocalizedMessageByMultipleKeys("insert.book.param","book.genres")
                + localizationService.getLocalizedMessage("use.comma"));
        String genres = ioStreamsProvider.readData();
        if (isInputValid(genres)) {
            String[] genresNames = genres.split("\\s*,\\s*");
            List<GenreDTO> genreDTOS = Arrays.stream(genresNames).map(GenreDTO::new).collect(Collectors.toList());
            bookDTO.setGenreDTOS(genreDTOS);
        } else {
            ioStreamsProvider.printInfo(localizationService.getLocalizedMessageByMultipleKeys("param.not.null","book.genres"));
            return true;
        }
        return false;
    }

    private boolean askAndAddAuthors(BookDTO bookDTO) {
        String authors = ioStreamsProvider.readData();
        if (isInputValid(authors)) {
            String[] authorsNames = authors.split("\\s*,\\s*");
            List<AuthorDTO> authorDTOS = Arrays.stream(authorsNames).map(AuthorDTO::new).collect(Collectors.toList());
            bookDTO.setAuthorDTOS(authorDTOS);
        } else {
            ioStreamsProvider.printInfo(localizationService.getLocalizedMessageByMultipleKeys("param.not.null","book.authors"));
            return true;
        }
        return false;
    }

    private boolean isInputValid(String input) {
        return (!StringUtils.isEmpty(input) && !StringUtils.isBlank(input));
    }
}
