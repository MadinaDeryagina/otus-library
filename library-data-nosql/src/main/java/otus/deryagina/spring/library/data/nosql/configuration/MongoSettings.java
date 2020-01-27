package otus.deryagina.spring.library.data.nosql.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("spring.data.mongodb")
public class MongoSettings {
    private int port;
    private String database;
    private String uri;
}
