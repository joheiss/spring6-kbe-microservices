package com.jovisco.microservicebeerservice.services;

import com.jovisco.microservicebeerservice.domain.BeerStyle;
import com.jovisco.microservicebeerservice.dtos.BeerDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {

    Flux<BeerDTO> getList(String name, BeerStyle style);

    Mono<BeerDTO> findById(Long id);

    Mono<BeerDTO> findByUpc(String upc);

    Mono<BeerDTO> create(BeerDTO toBeCreated);

    Mono<BeerDTO> update(Long id, BeerDTO toBeUpdated);

    Mono<Object> delete(Long id);
}
