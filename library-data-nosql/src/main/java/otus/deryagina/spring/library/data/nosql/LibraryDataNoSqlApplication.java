package otus.deryagina.spring.library.data.nosql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
@EnableConfigurationProperties
public class LibraryDataNoSqlApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryDataNoSqlApplication.class, args);
    }

}
