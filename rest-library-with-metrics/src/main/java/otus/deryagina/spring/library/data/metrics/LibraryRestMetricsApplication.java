package otus.deryagina.spring.library.data.metrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class LibraryRestMetricsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryRestMetricsApplication.class, args);
    }

}
