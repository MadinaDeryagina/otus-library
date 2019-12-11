package otus.deryagina.spring.libraryjdbc.interaction;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import otus.deryagina.spring.libraryjdbc.dto.BookDTO;
import otus.deryagina.spring.libraryjdbc.iostreams.IOStreamsProvider;
import otus.deryagina.spring.libraryjdbc.localizer.LocalizationService;

@Service
@RequiredArgsConstructor
public class InteractionServiceImpl implements InteractionService {

    private final IOStreamsProvider ioStreamsProvider;
    private final LocalizationService localizationService;

    @Override
    public BookDTO askToAddBook() {
        BookDTO bookDTO = new BookDTO();
        ioStreamsProvider.printInfo(localizationService.getLocalizedMessage("insert.book.title", null));
        String title = ioStreamsProvider.readData();
        if(isInputValid(title)){
            bookDTO.setTitle(title);
        }

        return null;
    }

    private boolean isInputValid(String input) {
        return (!StringUtils.isEmpty(input) && !StringUtils.isBlank(input));
    }
}
