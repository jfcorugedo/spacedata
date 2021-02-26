package com.jfcorugedo.spacedata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class RocketsResourceTest {

    private WebTestClient client;

    @BeforeEach
    void setUp(ApplicationContext context) {
        client = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void tryToGetNonExistentRocket() throws Exception {

        this.client.get()
                .uri("/api/v1/rockets/{name}", "non-existent-falcon")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();
    }

}
