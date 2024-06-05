package com.jovisco.microservicebeerservice.repositories;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.jovisco.microservicebeerservice.domain.Brewery;

@Repository
public interface BreweryRepository extends R2dbcRepository<Brewery, UUID> {
}
