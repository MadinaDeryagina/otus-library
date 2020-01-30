package otus.deryagina.spring.library.data.nosql.localizer;

public interface LocalizationService {

    String getLocalizedMessage(String key, Object... parameters);

    String getLocalizedMessageByMultipleKeys(String... keys);
}

