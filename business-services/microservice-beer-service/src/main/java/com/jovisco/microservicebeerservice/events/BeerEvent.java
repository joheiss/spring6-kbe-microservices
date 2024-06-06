package com.jovisco.microservicebeerservice.events;

import com.jovisco.microservicebeerservice.dtos.BeerDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BeerEvent {

    private BeerDTO beerDTO;
}
