package com.glauber.MatchService.domain.entity;

import lombok.*;

import java.math.BigDecimal;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEvent {
    private Long id;
    private String name;
    private String link;
    private BigDecimal price;
}
