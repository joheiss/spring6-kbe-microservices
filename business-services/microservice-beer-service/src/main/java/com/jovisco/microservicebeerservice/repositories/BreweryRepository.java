package com.jovisco.microservicebeerservice.repositories;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.jovisco.microservicebeerservice.domain.Brewery;

public interface BreweryRepository extends ReactiveCrudRepository<Brewery, UUID> {
}
