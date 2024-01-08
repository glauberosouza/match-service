package com.glauber.MatchService.templates.priceAlertTemplate;

public class PriceAlertRequestTemplate {
    public static PriceAlertRequest creation() {
        return new PriceAlertRequest(
                "Glauber",
                "Xbox",
                2000.0,
                "glauber@gmail.com"
        );
    }
}
