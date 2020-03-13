package otus.deryagina.spring.library.security.mvc.localizer;

public interface LocalizationService {

    String getLocalizedMessage(String key, Object... parameters);

}

