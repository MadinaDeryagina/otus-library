package otus.deryagina.spring.library.data.nosql.dao;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"otus.deryagina.spring.library.data.nosql.configuration", "otus.deryagina.spring.library.data.nosql.dao"})
abstract class AbstractRepositoryTest {

}
