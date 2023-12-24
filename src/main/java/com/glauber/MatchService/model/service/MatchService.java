package com.glauber.MatchService.model.service;

import com.glauber.MatchService.model.entity.PriceAlert;
import com.glauber.MatchService.model.entity.Product;
import com.glauber.MatchService.model.repository.PriceAlertRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
public class MatchService {
    @Autowired
    private PriceAlertRepository priceAlertRepository;
    @Autowired
    private PriceAlertRepository productRepository;


    public Optional<PriceAlert> verifyMatches(Product product) {

        var allPriceAlerts = priceAlertRepository.findAll();

        Optional<PriceAlert> first = allPriceAlerts.stream().filter(priceAlert -> priceAlert.getProductName().equals(product.getName())).findFirst();
        PriceAlert priceAlert = first.get();
        BigDecimal price = product.getPrice();
        double productValue = price.doubleValue();
        if (productValue <= priceAlert.getPriceRange()) {
            log.info("Match encontrado: Alerta de preço [{}], Produto [{}]", priceAlert, product);
            return Optional.of(priceAlert);
        }

        return Optional.empty();
    }
}