package com.jovisco.microservicebeerservice.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.jovisco.microservicebeerservice.domain.Beer;
import com.jovisco.microservicebeerservice.dtos.BeerDTO;
import com.jovisco.microservicebeerservice.mappers.BeerMapper;
import com.jovisco.microservicebeerservice.repositories.BeerRepository;
import com.jovisco.microservicebeerservice.services.BeerServiceImplTest;

import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BeerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    Beer testBeer;

    @BeforeEach
    void setUp() {
        testBeer = beerRepository.findAll().last().block();
    }

    @Test
    void testCreateBeer() {

        webTestClient
                .post().uri(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(BeerServiceImplTest.getTestBeerDto()), BeerDTO.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location");
    }

    @Test
    void testCreateBeerWithValidationError() {
        var beerDTO = BeerServiceImplTest.getTestBeerDto();
        beerDTO.setName(null);

        webTestClient
                .post().uri(BeerController.BEER_PATH)
                .body(Mono.just(beerDTO), BeerDTO.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testDeleteBeer() {

        var toBeDeleted = beerRepository.save(testBeer).block();

        webTestClient
                .delete().uri(BeerController.BEER_ID_PATH, toBeDeleted.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testGetAllBeers() {

        webTestClient
                .get().uri(BeerController.BEER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.size()").isEqualTo(9);
    }

    @Test
    void testGetBeerById() {

        var testBeer = beerRepository.findAll().last().block();

        webTestClient
                .get().uri(BeerController.BEER_ID_PATH, testBeer.getId())
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody(BeerDTO.class);
    }

    @Test
    void testGetBeerByIdNotFound() {

        webTestClient
                .get().uri(BeerController.BEER_ID_PATH, -1)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testGetBeerByUpc() {

        var testBeer = beerRepository.findAll().last().block();

        webTestClient
                .get().uri(BeerController.BEER_PATH
                        + "/upc/{upc}", testBeer.getUpc())
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody(BeerDTO.class);

    }

    @Test
    void testGetBeerByUpcNotFound() {

        webTestClient
                .get().uri(BeerController.BEER_PATH
                        + "/upc/{upc}", "Gibt's nicht!")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateBeer() {

        var toBeUpdated = beerRepository.save(testBeer).block();
        toBeUpdated.setName(toBeUpdated.getName() + " *** UPDATED ***");

        webTestClient
                .put().uri(BeerController.BEER_ID_PATH, toBeUpdated.getId())
                .body(Mono.just(beerMapper.toDto(toBeUpdated)), BeerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testUpdateBeerWithValidationError() {

        var toBeUpdated = beerRepository.save(testBeer).block();
        toBeUpdated.setName(null);

        webTestClient
                .put().uri(BeerController.BEER_ID_PATH, toBeUpdated.getId())
                .body(Mono.just(beerMapper.toDto(toBeUpdated)), BeerDTO.class)
                .exchange()
                .expectStatus().isBadRequest();
    }
}
