package otus.deryagina.spring.libraryjdbc.localizer;

public interface LocalizationService {

    String getLocalizedMessage(String key, String ...parameters);

    String getLocalizedMessage(String[] keys);
}

