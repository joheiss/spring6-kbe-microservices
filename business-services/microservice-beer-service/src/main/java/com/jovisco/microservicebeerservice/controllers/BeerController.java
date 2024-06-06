package com.jovisco.microservicebeerservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jovisco.microservicebeerservice.domain.BeerStyle;
import com.jovisco.microservicebeerservice.dtos.BeerDTO;
import com.jovisco.microservicebeerservice.services.BeerService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beers";
    public static final String BEER_ID_PATH = BEER_PATH + "/{id}";

    private final BeerService beerService;

    @GetMapping(BEER_PATH)
    Flux<BeerDTO> getAllBeers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BeerStyle style) {
        return beerService.getList(name, style);
    }

    @GetMapping(BEER_ID_PATH)
    Mono<BeerDTO> getBeerById(@PathVariable Long id) {
        return beerService.findById(id);
    }

    @GetMapping(BEER_PATH + "/upc/{upc}")
    Mono<BeerDTO> getBeerByUpc(@PathVariable String upc) {
        return beerService.findByUpc(upc);
    }

    @PostMapping(BEER_PATH)
    Mono<ResponseEntity<Void>> createBeer(@Validated @RequestBody BeerDTO createDTO) {
        return beerService.create(createDTO)
                .map(created -> {
                    // add the Location header
                    return ResponseEntity.created(
                            UriComponentsBuilder
                                    .fromPath(BEER_ID_PATH)
                                    .buildAndExpand(created.getId().toString())
                                    // .fromHttpUrl("http://localhost:8071/" + BEER_PATH + "/" + created.getId())
                                    // .build()
                                    .toUri())
                            .build();
                });
    }

    @PutMapping(BEER_ID_PATH)
    Mono<ResponseEntity<Void>> updateBeer(
            @PathVariable Long id,
            @Validated @RequestBody BeerDTO updateDTO) {
        return beerService.update(id, updateDTO)
                .map(updated -> ResponseEntity.noContent().build());
    }

    @DeleteMapping(BEER_ID_PATH)
    Mono<ResponseEntity<Void>> deleteBeer(@PathVariable Long id) {
        return beerService.delete(id)
                .thenReturn(ResponseEntity.noContent().build());
    }
}