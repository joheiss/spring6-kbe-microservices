package com.jovisco.microservicebeerservice.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import com.jovisco.microservicebeerservice.domain.Beer;
import com.jovisco.microservicebeerservice.domain.BeerStyle;
import com.jovisco.microservicebeerservice.dtos.BeerDTO;
import com.jovisco.microservicebeerservice.mappers.BeerMapper;
import com.jovisco.microservicebeerservice.mappers.BeerMapperImpl;

import reactor.core.publisher.Mono;

import static org.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
public class BeerServiceImplTest {

    @Autowired
    BeerService beerService;

    @Autowired
    BeerMapper beerMapper;

    BeerDTO beerDTO;

    @BeforeEach
    void setup() {
        beerDTO = beerMapper.toDto(getTestBeer());
    }

    @Test
    void testCreate() {

        var atomicBoolean = new AtomicBoolean(false);

        Mono<BeerDTO> created = beerService.create(beerDTO);
        created.subscribe(dto -> {
            System.out.println("*** CREATED KEY ***");
            System.out.println(dto.getId());
            System.out.flush();
            atomicBoolean.set(true);
            assertThat(dto.getId()).isNotNull();
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testDelete() {

        var toBeDeleted = getCreatedBeerDto();

        beerService.delete(toBeDeleted.getId()).block();

        assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> beerService.findById(toBeDeleted.getId()).block());

    }

    @Test
    void testFindById() {

        var created = getCreatedBeerDto();

        var atomicBoolean = new AtomicBoolean(false);

        var found = beerService.findById(created.getId());
        found.subscribe(dto -> {
            System.out.println(dto);
            System.out.flush();
            atomicBoolean.set(true);
            assertThat(dto.getId()).isNotNull();
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testFindByUpc() {

        // create a beer and update Upc accordingly
        final String upcToFind = "TEST0123456789TEST";
        var created = getCreatedBeerDto();
        created.setUpc(upcToFind);
        beerService.update(created.getId(), created).block();

        var atomicBoolean = new AtomicBoolean(false);

        var found = beerService.findByUpc(upcToFind);
        found.subscribe(dto -> {
            System.out.println(dto);
            System.out.flush();
            atomicBoolean.set(true);
            assertThat(dto.getId()).isNotNull();
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetList() {

        var found = beerService.getList(null, null);
        var count = found.count().block();
        System.out.println("Beers found: " + count);
        assertThat(count).isGreaterThan(0);

    }

    @Test
    void testGetListByName() {

        var created = getCreatedBeerDto();

        var found = beerService.getList(created.getName(), null);
        var count = found.count().block();
        System.out.println("Beers found: " + count);
        assertThat(count).isGreaterThan(0);
    }

    @Test
    void testGetListByStyle() {

        var created = getCreatedBeerDto();

        var found = beerService.getList(null, created.getStyle());
        var count = found.count().block();
        System.out.println("Beers found: " + count);
        assertThat(count).isGreaterThan(0);
    }

    @Test
    void testGetListByNameAndStyle() {

        var created = getCreatedBeerDto();

        var found = beerService.getList(created.getName(), created.getStyle());
        var count = found.count().block();
        System.out.println("Beers found: " + count);
        assertThat(count).isGreaterThan(0);
    }

    @Test
    void testUpdate() {

        final String upcToUpdate = "TESTUPDATE0123456789";
        var created = getCreatedBeerDto();
        created.setUpc(upcToUpdate);

        var atomicBoolean = new AtomicBoolean(false);

        var updated = beerService.update(created.getId(), created);
        updated.subscribe(dto -> {
            System.out.println(dto);
            System.out.flush();
            atomicBoolean.set(true);
            assertThat(dto.getUpc()).isEqualTo(upcToUpdate);
        });

        await().untilTrue(atomicBoolean);

    }

    public static Beer getTestBeer() {
        return Beer.builder()
                .name("Paulaner Hell")
                .style(BeerStyle.PALE_ALE)
                .price(new BigDecimal("12.34"))
                .quantityOnHand(23)
                .minOnHand(10)
                .quantityToBrew(1000)
                .upc(Instant.now().toString())
                .build();
    }

    public static BeerDTO getTestBeerDto() {
        return new BeerMapperImpl().toDto(getTestBeer());
    }

    public BeerDTO getCreatedBeerDto() {
        return beerService.create(getTestBeerDto()).block();
    }
}
