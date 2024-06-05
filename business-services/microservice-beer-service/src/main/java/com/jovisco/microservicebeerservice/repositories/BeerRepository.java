package com.jovisco.microservicebeerservice.repositories;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.jovisco.microservicebeerservice.domain.Beer;
import com.jovisco.microservicebeerservice.domain.BeerStyle;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerRepository extends ReactiveCrudRepository<Beer, UUID> {

    Flux<Beer> findAllByName(String name);

    Flux<Beer> findAllByStyle(BeerStyle style);

    Flux<Beer> findAllByNameAndStyle(String name, BeerStyle style);

    Mono<Beer> findByUpc(String upc);
}
