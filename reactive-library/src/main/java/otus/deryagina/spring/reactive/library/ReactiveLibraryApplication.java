package otus.deryagina.spring.reactive.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
@EnableConfigurationProperties
public class ReactiveLibraryApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactiveLibraryApplication.class, args);
    }

}
