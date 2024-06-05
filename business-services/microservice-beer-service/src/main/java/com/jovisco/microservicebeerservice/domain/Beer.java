package com.jovisco.microservicebeerservice.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
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
@Entity
// @Table(name = "beers")
public class Beer {

    @Id
    @GeneratedValue(generator = "UUID")
    // @UuidGenerator(style = UuidGenerator.Style.TIME)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    // @Type(type = "org.hibernate.type.UUIDCharType")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public boolean isNew() {
        return this.id == null;
    }

    @NotBlank
    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BeerStyle style;

    @NotBlank
    @NotNull
    @Size(max = 36)
    @Column(unique = true)
    private String upc;

    private BigDecimal price;

    private Integer minOnHand;
    private Integer quantityToBrew;
    private Integer quantityOnHand;
}
