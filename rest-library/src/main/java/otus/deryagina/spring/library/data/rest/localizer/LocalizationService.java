package otus.deryagina.spring.library.data.rest.localizer;

public interface LocalizationService {

    String getLocalizedMessage(String key, Object... parameters);

}

