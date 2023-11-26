package com.glauber.MatchService.service;

import com.glauber.MatchService.domain.entity.PriceAlertEvent;
import com.glauber.MatchService.domain.entity.ProductEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {
    @Mock
    private EmailService emailService;
    @InjectMocks
    private MatchService matchService;

    @Test
    @DisplayName("Deve verificar se teve match entre o preço do produto e o range de preço do Alerta")
    public void shouldVerifyIfHadMatchBetweenProductPriceAndRangeAlert() {
        // Arrange
        PriceAlertEvent priceAlertEvent = new PriceAlertEvent();
        ProductEvent productEvent = new ProductEvent();
        priceAlertEvent.setPriceRange(100.0);
        productEvent.setPrice(BigDecimal.valueOf(90.0));

        // Act
        matchService.verifyMatches(priceAlertEvent, productEvent);

        //Assert
        verify(emailService, times(1)).sendMatchEmail(priceAlertEvent, productEvent);
    }

    @Test
    @DisplayName("Deve falhar caso não encontre match entre o preço do produto e o range de preço do alerta")
    public void shouldFailIfDontfindMatchBetweenProductPriceAndRangeAlert() {
        // Arrange
        PriceAlertEvent priceAlertEvent = new PriceAlertEvent();
        ProductEvent productEvent = new ProductEvent();
        priceAlertEvent.setPriceRange(100.0);
        productEvent.setPrice(BigDecimal.valueOf(150.0));

        // Act
        matchService.verifyMatches(priceAlertEvent, productEvent);

        //Assert
        verify(emailService, never()).sendMatchEmail(priceAlertEvent, productEvent);
    }
}