package com.glauber.MatchService.domain.entity;

import com.glauber.MatchService.listeners.PriceAlertListener;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class PriceAlertEvent {
    private Long id;
    private String name;
    private String productName;
    private Double priceRange;
    private String email;
}