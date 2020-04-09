package otus.deryagina.spring.library.data.docker.localizer;

public interface LocalizationService {

    String getLocalizedMessage(String key, Object... parameters);

}

