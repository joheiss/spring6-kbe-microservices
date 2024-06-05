package com.jovisco.microservicebeerservice.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jovisco.microservicebeerservice.domain.Brewery;

public interface BreweryRepository extends JpaRepository<Brewery, UUID> {}
