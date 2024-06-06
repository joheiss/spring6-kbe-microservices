package com.jovisco.microservicebeerservice.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "beers")
public class Beer {

    @Id
    private Long id;

    // @Version
    private Long version;

    @CreatedDate
    // @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public boolean isNew() {
        return this.id == null;
    }

    @NotBlank
    @NotNull
    @Size(max = 50)
    // @Column(length = 50)
    private String name;

    @NotNull
    // @Enumerated(EnumType.STRING)
    private BeerStyle style;

    @NotBlank
    @NotNull
    @Size(max = 36)
    // @Column(unique = true)
    private String upc;

    private BigDecimal price;

    private Integer minOnHand;
    private Integer quantityToBrew;
    private Integer quantityOnHand;
}
