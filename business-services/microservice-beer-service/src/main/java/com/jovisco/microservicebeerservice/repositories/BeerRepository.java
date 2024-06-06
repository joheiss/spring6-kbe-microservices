package com.jovisco.microservicebeerservice.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.jovisco.microservicebeerservice.domain.Beer;
import com.jovisco.microservicebeerservice.domain.BeerStyle;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BeerRepository extends ReactiveCrudRepository<Beer, Long> {

    Flux<Beer> findAllByName(String name);

    Flux<Beer> findAllByStyle(BeerStyle style);

    Flux<Beer> findAllByNameAndStyle(String name, BeerStyle style);

    Mono<Beer> findByUpc(String upc);
}
