package com.glauber.MatchService.domain.entity;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
@Data
@Getter
public class ProductEvent {
    private Long id;
    private String name;
    private String link;
    private BigDecimal price;
}
