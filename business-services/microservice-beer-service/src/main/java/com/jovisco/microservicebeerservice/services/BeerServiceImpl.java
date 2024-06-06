package com.jovisco.microservicebeerservice.services;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.jovisco.microservicebeerservice.domain.BeerStyle;
import com.jovisco.microservicebeerservice.dtos.BeerDTO;
import com.jovisco.microservicebeerservice.mappers.BeerMapper;
import com.jovisco.microservicebeerservice.repositories.BeerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerListCache")
    @Override
    public Flux<BeerDTO> getList(String name, BeerStyle style) {

        log.debug("Get list of beers ...");

        if (StringUtils.hasText(name) && style != null) {
            // search for beer by name and style
            return beerRepository
                    .findAllByNameAndStyle(name, style)
                    .map(beerMapper::toDto);
        } else if (StringUtils.hasText(name) && style == null) {
            // search for beer by name
            return beerRepository.findAllByName(name).map(beerMapper::toDto);
        } else if (!StringUtils.hasText(name) && style != null) {
            // search beer by style
            return beerRepository.findAllByStyle(style).map(beerMapper::toDto);
        } else {
            return beerRepository.findAll().map(beerMapper::toDto);
        }
    }

    @Cacheable(cacheNames = "beerCache", key = "#id")
    @Override
    public Mono<BeerDTO> findById(Long id) {

        log.debug("Find beer by id ...");

        return beerRepository
                .findById(id)
                .map(beerMapper::toDto)
                .switchIfEmpty(
                        Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Beer not found. ID: " + id)));
    }

    @Cacheable(cacheNames = "beerUpcCache")
    @Override
    public Mono<BeerDTO> findByUpc(String upc) {

        log.debug("Find beer by Upc ...");

        return beerRepository
                .findByUpc(upc)
                .map(beerMapper::toDto)
                .switchIfEmpty(
                        Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Beer not found. Upc: " + upc)));
    }

    @Override
    public Mono<BeerDTO> create(BeerDTO createDTO) {

        log.debug("Create new beer ...");

        return beerRepository
                .save(beerMapper.fromDto(createDTO))
                .map(beerMapper::toDto);
    }

    @Override
    public Mono<BeerDTO> update(Long id, BeerDTO updateDTO) {

        log.debug("Update beer ...");

        return beerRepository
                .findById(id)
                .switchIfEmpty(
                        Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Beer not found. ID: " + id)))
                .map(beer -> {
                    beer.setName(updateDTO.getName());
                    beer.setStyle(updateDTO.getStyle());
                    beer.setPrice(updateDTO.getPrice());
                    beer.setUpc(updateDTO.getUpc());
                    return beer;
                })
                .flatMap(beerRepository::save)
                .map(beerMapper::toDto);
    }

    @Override
    public Mono<Object> delete(Long id) {

        log.debug("Delete beer ...");

        return beerRepository.findById(id)
                .switchIfEmpty(
                        Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Beer not found. UUID: " + id)))
                .map(beer -> beer.getId())
                .flatMap(beerRepository::deleteById);
    }

}
