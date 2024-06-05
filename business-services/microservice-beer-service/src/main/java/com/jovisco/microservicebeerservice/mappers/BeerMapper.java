package com.jovisco.microservicebeerservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.jovisco.microservicebeerservice.domain.Beer;
import com.jovisco.microservicebeerservice.dtos.BeerDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BeerMapper {

    BeerDTO toDto(Beer beer);

    BeerDTO toDtoWithInventory(Beer beer);

    Beer fromDto(BeerDTO dto);
}
