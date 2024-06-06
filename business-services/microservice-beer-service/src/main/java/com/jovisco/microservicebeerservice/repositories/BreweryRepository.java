package com.jovisco.microservicebeerservice.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.jovisco.microservicebeerservice.domain.Brewery;

@Repository
public interface BreweryRepository extends ReactiveCrudRepository<Brewery, Long> {
}
