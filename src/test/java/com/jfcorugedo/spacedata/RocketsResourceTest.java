package com.jfcorugedo.spacedata;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@ContextConfiguration(initializers = RocketsResourceTest.ConfigInitializer.class)
@Slf4j
class RocketsResourceTest {

    public static WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());

    public static final String GET_ROCKET_ENDPOINT = "/api/v1/rockets/{name}";
    private WebTestClient client;

    @BeforeAll
    static void startWireMock() {
        wireMockServer.start();
        log.debug("WireMock server started at port {}", wireMockServer.port());
    }

    @AfterAll
    static void tearDownWireMock() {
        log.debug("Stopping WireMock server");
        wireMockServer.stop();
    }

    @BeforeEach
    public void setUp(ApplicationContext context) {
        client = WebTestClient.bindToApplicationContext(context).build();
        wireMockServer.resetMappings();
    }

    @Test
    void tryToGetNonExistentRocket() {

        wireMockServer.givenThat(
                get(urlEqualTo("/non-existent-falcon"))
                .willReturn(
                        aResponse().withStatus(404)
                )
        );

        this.client.get()
                .uri(GET_ROCKET_ENDPOINT, "non-existent-falcon")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();
    }

    @Test
    void getValidRocket() {

        wireMockServer.givenThat(
                get(urlEqualTo("/falcon9"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader(CONTENT_TYPE, APPLICATION_JSON.toString())
                                        .withBodyFile("rockets/falcon9.json")
                        )
        );

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

    static class ConfigInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    String.format("spacexdata.url=http://localhost:%d/", wireMockServer.port())
            ).applyTo(applicationContext);
        }
    }
}
