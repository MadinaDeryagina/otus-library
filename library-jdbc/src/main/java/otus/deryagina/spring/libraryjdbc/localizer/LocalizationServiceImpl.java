package otus.deryagina.spring.libraryjdbc.localizer;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import otus.deryagina.spring.libraryjdbc.configuration.ApplicationSettings;


import java.util.Locale;

@Service
public class LocalizationServiceImpl implements LocalizationService {

    private final MessageSource messageSource;
    private final ApplicationSettings applicationSettings;
    private final Locale locale;

    public LocalizationServiceImpl(MessageSource messageSource, ApplicationSettings applicationSettings) {
        this.messageSource = messageSource;
        this.applicationSettings = applicationSettings;
        String localeName = this.applicationSettings.getLocaleName();
        this.locale = new Locale(localeName);
    }

    @Override
    public String getLocalizedMessage(String key, String[] parameters) {
        return messageSource.getMessage(key, parameters, locale);
    }

}