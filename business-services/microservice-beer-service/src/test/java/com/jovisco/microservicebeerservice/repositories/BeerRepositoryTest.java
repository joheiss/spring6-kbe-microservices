package com.jovisco.microservicebeerservice.repositories;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import com.jovisco.microservicebeerservice.config.DatabaseConfig;
import com.jovisco.microservicebeerservice.domain.Beer;
import com.jovisco.microservicebeerservice.services.BeerServiceImplTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicBoolean;

@DataR2dbcTest
@Import(DatabaseConfig.class)
public class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    Beer testBeer;

    @BeforeEach
    void setUp() {
        var atomicBoolean = new AtomicBoolean(false);

        var toBeCreated = BeerServiceImplTest.getTestBeer();
        beerRepository.save(toBeCreated)
                .subscribe(beer -> {
                    System.out.println(beer);
                    testBeer = beer;
                    atomicBoolean.set(true);
                });

        Awaitility.await().untilTrue(atomicBoolean);
    }

    @Test
    void testFindAllByName() {

        var count = beerRepository.findAllByName(testBeer.getName()).count().block();
        assertThat(count).isGreaterThan(0);
    }

    @Test
    void testFindAllByNameAndStyle() {

        var count = beerRepository.findAllByNameAndStyle(testBeer.getName(), testBeer.getStyle()).count().block();
        assertThat(count).isGreaterThan(0);
    }

    @Test
    void testFindAllByStyle() {

        var count = beerRepository.findAllByStyle(testBeer.getStyle()).count().block();
        assertThat(count).isGreaterThan(0);
    }

    @Test
    void testFindByUpc() {

        var found = beerRepository.findByUpc(testBeer.getUpc()).block();
        assertThat(found.getId()).isEqualTo(testBeer.getId());
    }
}
