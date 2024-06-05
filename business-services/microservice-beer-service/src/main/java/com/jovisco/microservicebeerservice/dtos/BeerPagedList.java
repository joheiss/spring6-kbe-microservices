package com.jovisco.microservicebeerservice.dtos;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class BeerPagedList extends PageImpl<BeerDTO> {

    public BeerPagedList(List<BeerDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public BeerPagedList(List<BeerDTO> content) {
        super(content);
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BeerPagedList(
        List<BeerDTO> content,
        int number,
        int size,
        Long totalElements,
        JsonNode pageable,
        boolean last,
        int totalPages,
        JsonNode sort,
        boolean first,
        int numberOfELements
    ) {
        super(content, PageRequest.of(number, size), totalElements);
    }
}
