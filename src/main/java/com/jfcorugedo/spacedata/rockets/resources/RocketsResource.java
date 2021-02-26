package com.jfcorugedo.spacedata.rockets.resources;

import com.jfcorugedo.spacedata.rockets.dto.Rocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException.NotFound;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/rockets")
@Slf4j
public class RocketsResource {

    @Value("${spacexdata.url}")
    private String baseUrl;

    @GetMapping("{name}")
    public Mono<ResponseEntity<Rocket>> getRocket(@PathVariable String name) {
        log.info("Getting rocket ".concat(baseUrl.concat(name)));

        WebClient client = WebClient.create(baseUrl.concat(name));

        Mono<Rocket> result = client.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Rocket.class);

        return result
                .doOnNext(rocket -> log.info(rocket.toString()))
                .map(ResponseEntity::ok)
                .onErrorReturn(NotFound.class, ResponseEntity.notFound().build());
    }
}
