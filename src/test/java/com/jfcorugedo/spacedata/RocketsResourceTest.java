package com.jfcorugedo.spacedata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
class RocketsResourceTest {

    public static final String GET_ROCKET_ENDPOINT = "/api/v1/rockets/{name}";
    private WebTestClient client;

    @BeforeEach
    public void setUp(ApplicationContext context) {
        client = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void tryToGetNonExistentRocket() throws Exception {

        this.client.get()
                .uri(GET_ROCKET_ENDPOINT, "non-existent-falcon")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();
    }

    @Test
    void getValidRocket() {

        this.client.get()
                .uri("/api/v1/rockets/{name}", "falcon9")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.rocket_name").isEqualTo("Falcon 9")
                .jsonPath("$.cost_per_launch").isEqualTo(50000000)
                .jsonPath("$.success_rate_pct").isEqualTo(97);
    }
}
