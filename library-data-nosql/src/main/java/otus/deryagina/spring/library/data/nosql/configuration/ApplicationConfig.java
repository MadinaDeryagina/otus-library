package otus.deryagina.spring.library.data.nosql.configuration;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    private static final String CHANGELOGS_PACKAGE = "otus.deryagina.spring.library.data.nosql.changelogs";

    @Bean
    public Mongock mongock(MongoSettings mongoSettings, MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, mongoSettings.getDatabase(), CHANGELOGS_PACKAGE)
                .build();
    }
}
