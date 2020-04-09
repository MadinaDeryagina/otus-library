package otus.deryagina.spring.library.data.docker.localizer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {

    private final MessageSource messageSource;

    @Override
    public String getLocalizedMessage(String key, Object... parameters) {
        return messageSource.getMessage(key, parameters, LocaleContextHolder.getLocale());
    }


}