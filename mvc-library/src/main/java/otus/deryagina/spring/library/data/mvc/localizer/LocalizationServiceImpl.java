package otus.deryagina.spring.library.data.mvc.localizer;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import otus.deryagina.spring.library.data.mvc.configuration.ApplicationSettings;

import java.util.ArrayList;
import java.util.List;
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
    public String getLocalizedMessage(String key, Object... parameters) {
        return messageSource.getMessage(key, parameters, locale);
    }

    @Override
    public String getLocalizedMessageByMultipleKeys(String... keys) {
        List<String> params= new ArrayList<>();
        for (int i = keys.length-1; i >= 0 ; i--) {
            params.add(getLocalizedMessage(keys[i], params.toArray()));
        }
        return getLocalizedMessage(keys[0],params.toArray());
    }


}