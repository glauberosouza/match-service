package com.glauber.MatchService.model.entity;

import com.glauber.MatchService.controller.response.PriceAlertResponse;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class PriceAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price_range")
    private Double priceRange;

    private String email;

    public static PriceAlertResponse toPriceAlertResponse(PriceAlert priceAlert) {
        var priceAlertResponse = new PriceAlertResponse();
        priceAlertResponse.setId(priceAlert.getId());
        priceAlertResponse.setName(priceAlert.getName());
        priceAlertResponse.setProductName(priceAlert.getProductName());
        priceAlertResponse.setPriceRange(priceAlert.getPriceRange());
        priceAlertResponse.setEmail(priceAlert.getEmail());
        return priceAlertResponse;
    }
}