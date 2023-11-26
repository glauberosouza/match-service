package com.glauber.MatchService.domain.entity;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceAlertEvent {
    private Long id;
    private String name;
    private String productName;
    private Double priceRange;
    private String email;
}