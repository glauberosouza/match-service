package com.glauber.MatchService.service;

import com.glauber.MatchService.domain.entity.PriceAlertEvent;
import com.glauber.MatchService.domain.entity.ProductEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO: 2 MÉTODOS PARA VALIDAR SE TEVE MATCH NOS PREÇOS DAS ENTIDADES.
@Slf4j
@Service
public class MatchService {
    @Autowired
    private EmailService emailService;

    public void verifyMatches(PriceAlertEvent priceAlertEvent, ProductEvent productEvent) {

        if (isMatch(priceAlertEvent, productEvent)) {
            emailService.sendMatchEmail(priceAlertEvent, productEvent);
        } else {
            log.info("Nenhuma correspondência encontrada para o alerta de preço: {}", priceAlertEvent);
        }
    }

    private boolean isMatch(PriceAlertEvent priceAlertEvent, ProductEvent productEvent) {
        Double priceRange = priceAlertEvent.getPriceRange();
        Double productPrice = productEvent.getPrice().doubleValue();
        return productPrice < priceRange;
    }
}

