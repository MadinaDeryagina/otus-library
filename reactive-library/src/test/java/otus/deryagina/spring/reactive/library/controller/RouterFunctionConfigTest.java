package otus.deryagina.spring.reactive.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;

@SpringBootTest
class RouterFunctionConfigTest {
    @Autowired
    private RouterFunction route;

    @Test
    void testRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get()
                .uri("/func/books")
                .exchange()
                .expectStatus()
                .isOk();
    }
}