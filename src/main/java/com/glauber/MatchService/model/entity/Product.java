package com.glauber.MatchService.model.entity;

import com.glauber.MatchService.controller.response.ProductResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String link;
    private BigDecimal price;

    public static ProductResponse toProductResponse(Product product) {
        var productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setLink(product.getLink());
        productResponse.setPrice(product.getPrice());
        return productResponse;
    }
}