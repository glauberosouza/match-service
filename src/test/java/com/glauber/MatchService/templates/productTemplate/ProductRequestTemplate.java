package com.glauber.MatchService.templates.productTemplate;

import java.math.BigDecimal;

public class ProductRequestTemplate {
    public static ProductRequest creation() {
        return new ProductRequest(
                "Xbox",
                "www.store.com,br/xbox",
                BigDecimal.valueOf(2000.00)
        );
    }
}
