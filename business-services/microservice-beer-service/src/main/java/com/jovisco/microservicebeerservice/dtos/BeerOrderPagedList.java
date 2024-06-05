package com.jovisco.microservicebeerservice.dtos;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class BeerOrderPagedList extends PageImpl<BeerOrderDTO> {

    public BeerOrderPagedList(
        List<BeerOrderDTO> content,
        Pageable pageable,
        long total
    ) {
        super(content, pageable, total);
    }

    public BeerOrderPagedList(List<BeerOrderDTO> content) {
        super(content);
    }

}
