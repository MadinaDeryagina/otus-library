package otus.deryagina.spring.library.data.metrics.localizer;

public interface LocalizationService {

    String getLocalizedMessage(String key, Object... parameters);

}

