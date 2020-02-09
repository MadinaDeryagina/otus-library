package otus.deryagina.spring.library.data.mvc.localizer;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.descriptor.LocalResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {

    private final MessageSource messageSource;

    @Override
    public String getLocalizedMessage(String key, Object... parameters) {
        return messageSource.getMessage(key, parameters, LocaleContextHolder.getLocale());
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