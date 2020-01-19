package otus.deryagina.spring.libraryjdbc.localizer;

public interface LocalizationService {

    String getLocalizedMessage(String key, Object ...parameters);

    String getLocalizedMessageByMultipleKeys(String... keys);
}

