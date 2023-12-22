package com.glauber.MatchService.model.service;

import com.glauber.MatchService.model.entity.PriceAlert;
import com.glauber.MatchService.model.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO: 2 MÉTODOS PARA VALIDAR SE TEVE MATCH NOS PREÇOS DAS ENTIDADES.
@Slf4j
@Service
public class MatchService {
    @Autowired
    private EmailService emailService;

    public void verifyMatches(PriceAlert priceAlert, Product product) {

        if (isMatch(priceAlert, product)) {
            emailService.sendMatchEmail(priceAlert, product);
        } else {
            log.info("Nenhuma correspondência encontrada para o alerta de preço: {}", priceAlert);
        }
    }

    private boolean isMatch(PriceAlert priceAlert, Product product) {
        Double priceRange = priceAlert.getPriceRange();
        double productPrice = product.getPrice().doubleValue();
        boolean match = productPrice < priceRange;

        if (match) {
            log.info("Match encontrado: Alerta de preço [{}], Produto [{}]", priceAlert, product);
        } else {
            log.debug("Nenhum match encontrado");
        }

        return match;
    }
}

