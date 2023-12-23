package com.glauber.MatchService.model.service;

import com.glauber.MatchService.model.entity.PriceAlert;
import com.glauber.MatchService.model.entity.Product;
import com.glauber.MatchService.model.repository.PriceAlertRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
//TODO NÃO ESTÁ DANDO CERTO A COMPARAÇÃO DO PREÇO VERIFICAR.

@Slf4j
@Service
public class MatchService {
    @Autowired
    private PriceAlertRepository priceAlertRepository;
    @Autowired
    private PriceAlertRepository productRepository;


    public Optional<PriceAlert> verifyMatches(Product product) {
        var allPriceAlerts = priceAlertRepository.findAll();

        return allPriceAlerts.stream()
                .filter(alert -> Objects.equals(alert.getName(), product.getName()))
                .findFirst()
                .filter(alert -> BigDecimal.valueOf(alert.getPriceRange()).compareTo(product.getPrice()) < 0)
                .map(alert -> {
                    log.info("Match encontrado: Alerta de preço [{}], Produto [{}]", alert, product);
                    return alert;
                });
    }
}