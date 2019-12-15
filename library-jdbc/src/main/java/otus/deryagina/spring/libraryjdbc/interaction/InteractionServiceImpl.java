package otus.deryagina.spring.libraryjdbc.interaction;

import lombok.RequiredArgsConstructor;
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
            bookService.addAsNewBook(bookDTO);
            return;
        }
        List<BookDTO> exactlySameBook = booksWithSameTitle.stream().filter(x -> x.equals(bookDTO)).collect(Collectors.toList());
        if (!exactlySameBook.isEmpty()) {
            ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("same.book.in.lib", new String[]{exactlySameBook.toString()}));
            return;
        }
        ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("found.books.with.same.title", new String[]{booksWithSameTitle.toString()}));
        String yesOrNo = ioStreamsProvider.readData();
        if (yesOrNo.equalsIgnoreCase(localizationService.getLocalizedMessage("yes", null))) {
            bookService.addAsNewBook(bookDTO);
        } else if (yesOrNo.equalsIgnoreCase(localizationService.getLocalizedMessage("no", null))){
            //which book do you want yo update
            ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("which.to.update",null));
            //ioStreamsProvider.printInfo();
        }
    }

    //TODO: сначала сделать валидацию всех входных парамтеров, потом формировать dto ( вынести в отдельный сервис)
    private BookDTO createBookDTOByInputData() {
        BookDTO bookDTO = new BookDTO();
        ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("insert.book.param", new String[]{localizationService.getLocalizedMessage("book.title", null)}));
        String title = ioStreamsProvider.readData();
        if (isInputValid(title)) {
            bookDTO.setTitle(title);
        } else {
            ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("param.not.null", new String[]{localizationService.getLocalizedMessage("book.title", null)}));
            return null;
        }
        ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("insert.book.param", new String[]{localizationService.getLocalizedMessage("book.authors", null)})
                + localizationService.getLocalizedMessage("use.comma", null));
        String authors = ioStreamsProvider.readData();
        if (isInputValid(authors)) {
            String[] authorsNames = authors.split("\\s*,\\s*");
            List<AuthorDTO> authorDTOS = Arrays.stream(authorsNames).map(AuthorDTO::new).collect(Collectors.toList());
            bookDTO.setAuthorDTOS(authorDTOS);
        } else {
            ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("param.not.null", new String[]{localizationService.getLocalizedMessage("book.authors", null)}));
            return null;
        }
        ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("insert.book.param", new String[]{localizationService.getLocalizedMessage("book.genres", null)})
                + localizationService.getLocalizedMessage("use.comma", null));
        String genres = ioStreamsProvider.readData();
        if (isInputValid(genres)) {
            String[] genresNames = genres.split("\\s*,\\s*");
            List<GenreDTO> genreDTOS = Arrays.stream(genresNames).map(GenreDTO::new).collect(Collectors.toList());
            bookDTO.setGenreDTOS(genreDTOS);
        } else {
            ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("param.not.null", new String[]{localizationService.getLocalizedMessage("book.genres", null)}));
            return null;
        }
        return bookDTO;
    }

    private boolean isInputValid(String input) {
        return (!StringUtils.isEmpty(input) && !StringUtils.isBlank(input));
    }
}
