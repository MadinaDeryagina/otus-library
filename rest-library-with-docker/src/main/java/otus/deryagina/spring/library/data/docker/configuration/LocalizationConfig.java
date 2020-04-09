package otus.deryagina.spring.library.data.docker.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
public class LocalizationConfig {

    @Bean
    public LocaleResolver localeResolver(){
        CookieLocaleResolver resolver
                = new CookieLocaleResolver();
        resolver.setDefaultLocale(
                new Locale("en"));
        resolver.setCookieName("locale");
        return resolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor=
                new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }
}
