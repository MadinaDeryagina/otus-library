package otus.deryagina.spring.libraryjpa.localizer;

public interface LocalizationService {

    String getLocalizedMessage(String key, String[] parameters);

}

