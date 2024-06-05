package com.jovisco.microservicebeerservice.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jovisco.microservicebeerservice.domain.Beer;
import com.jovisco.microservicebeerservice.domain.BeerStyle;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

    Page<Beer> findAllByName(String name, Pageable pageable);
    Page<Beer> findAllByStyle(BeerStyle style, Pageable pageable);
    Page<Beer> findAllByNameAndStyle(String name, BeerStyle style, Pageable pageable);
    Beer findByUpc(String upc);
}
