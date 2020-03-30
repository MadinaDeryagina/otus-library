package otus.deryagina.spring.library.security.acl.mvc.localizer;

public interface LocalizationService {

    String getLocalizedMessage(String key, Object... parameters);

}

