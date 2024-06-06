package com.jovisco.microservicebeerservice.bootstrap;

import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.jovisco.microservicebeerservice.domain.Beer;
import com.jovisco.microservicebeerservice.domain.BeerStyle;
import com.jovisco.microservicebeerservice.domain.Brewery;
import com.jovisco.microservicebeerservice.repositories.BeerRepository;
import com.jovisco.microservicebeerservice.repositories.BreweryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {

        public static final String BEER_1_UPC = "0631234200036";
        public static final String BEER_2_UPC = "0631234300019";
        public static final String BEER_3_UPC = "0083783375213";
        public static final String BEER_4_UPC = "0083783375232";
        public static final String BEER_5_UPC = "0083782275213";
        public static final String BEER_6_UPC = "0023763375222";
        public static final String BEER_7_UPC = "0783783332215";
        public static final String BEER_8_UPC = "0883783375217";
        public static final String BEER_9_UPC = "0983783375443";

        private final BreweryRepository breweryRepository;
        private final BeerRepository beerRepository;
        private final CacheManager cacheManager;

        @Override
        public void run(String... args) throws Exception {

                log.debug("Initializing Data");

                loadBreweryData();
                loadBeerData();

                cacheManager.getCache("beerListCache").clear();
                log.debug("Data Initialized. Beer Records loaded {}", beerRepository.count().block());
        }

        private void loadBeerData() {

                beerRepository.deleteAll().block();

                beerRepository.save(Beer.builder()
                                // .id(UUID.randomUUID())
                                .name("Mango Bobs")
                                .style(BeerStyle.IPA)
                                .minOnHand(12)
                                .quantityToBrew(200)
                                .quantityOnHand(500)
                                .upc(BEER_1_UPC)
                                .price(new BigDecimal("9.87"))
                                .build())
                                .block();

                beerRepository.save(Beer.builder()
                                // .id(UUID.randomUUID())
                                .name("Galaxy Cat")
                                .style(BeerStyle.PALE_ALE)
                                .minOnHand(12)
                                .quantityToBrew(200)
                                .upc(BEER_2_UPC)
                                .price(new BigDecimal("8.76"))
                                .build())
                                .block();

                beerRepository.save(Beer.builder()
                                // .id(UUID.randomUUID())
                                .name("Pinball Porter")
                                .style(BeerStyle.PORTER)
                                .minOnHand(12)
                                .quantityToBrew(200)
                                .upc(BEER_3_UPC)
                                .price(new BigDecimal("7.65"))
                                .build())
                                .block();

                beerRepository.save(Beer.builder()
                                // .id(UUID.randomUUID())
                                .name("Golden Buddha")
                                .style(BeerStyle.IPA)
                                .minOnHand(12)
                                .quantityToBrew(300)
                                .upc(BEER_4_UPC)
                                .price(new BigDecimal("6.54"))
                                .build())
                                .block();

                beerRepository.save(Beer.builder()
                                // .id(UUID.randomUUID())
                                .name("Cage Blond")
                                .style(BeerStyle.ALE)
                                .minOnHand(12)
                                .quantityToBrew(200)
                                .upc(BEER_5_UPC)
                                .price(new BigDecimal("5.43"))
                                .build())
                                .block();

                beerRepository.save(Beer.builder()
                                // .id(UUID.randomUUID())
                                .name("Amarmillo IPA")
                                .style(BeerStyle.IPA)
                                .minOnHand(12)
                                .quantityToBrew(200)
                                .upc(BEER_6_UPC)
                                .price(new BigDecimal("4.32"))
                                .build())
                                .block();

                beerRepository.save(Beer.builder()
                                // .id(UUID.randomUUID())
                                .name("King Krush")
                                .style(BeerStyle.IPA)
                                .minOnHand(12)
                                .quantityToBrew(200)
                                .upc(BEER_7_UPC)
                                .price(new BigDecimal("3.21"))
                                .build())
                                .block();

                beerRepository.save(Beer.builder()
                                // .id(UUID.randomUUID())
                                .name("Static IPA")
                                .style(BeerStyle.IPA)
                                .minOnHand(12)
                                .quantityToBrew(200)
                                .upc(BEER_8_UPC)
                                .price(new BigDecimal("2.10"))
                                .build())
                                .block();

                beerRepository.save(Beer.builder()
                                // .id(UUID.randomUUID())
                                .name("Grand Central")
                                .style(BeerStyle.ALE)
                                .minOnHand(12)
                                .quantityToBrew(200)
                                .upc(BEER_9_UPC)
                                .price(new BigDecimal("1.09"))
                                .build())
                                .block();

        }

        private void loadBreweryData() {

                breweryRepository.deleteAll().block();

                var count = breweryRepository.count().block();
                if (count == 0) {
                        log.debug("Load breweries to database ...");
                        breweryRepository.save(Brewery
                                        .builder()
                                        // .id(UUID.randomUUID())
                                        .name("Cage Brewing")
                                        .build())
                                        .block();
                }
        }

}
