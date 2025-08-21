package springboot.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;
import springboot.domain.Anime;

@Log4j2
public class SpringClient {
    private static final String url1 = "http://localhost:8080/animes/2";
    private static final String url2 = "http://localhost:8080/animes/list_no_pageable";
    private static final String urlPost = "http://localhost:8080/animes";

    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity(url1, Anime.class);
        log.info(entity.getBody());
        Anime object = new RestTemplate().getForObject(url1, Anime.class, 2);
        log.info(object);

        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange(url2,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Anime>>() {
                });
        log.info(exchange.getBody());

        Anime anime = Anime.builder().name("Flinstons").build();
        ResponseEntity<Anime> animeSaved = new RestTemplate().exchange(urlPost, HttpMethod.POST,
                new HttpEntity<>(anime),
                Anime.class);
        log.info(animeSaved.getBody());
    }
}
