package com.glauber.MatchService.service;

import com.glauber.MatchService.model.entity.PriceAlert;
import com.glauber.MatchService.model.entity.Product;
import com.glauber.MatchService.model.service.EmailService;
import com.glauber.MatchService.model.service.MatchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

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
        PriceAlert priceAlert = new PriceAlert();
        Product product = new Product();
        priceAlert.setPriceRange(100.0);
        product.setPrice(BigDecimal.valueOf(90.0));

        // Act
        matchService.verifyMatches(priceAlert, product);

        //Assert
        verify(emailService, times(1)).sendMatchEmail(priceAlert, product);
    }

    @Test
    @DisplayName("Deve falhar caso não encontre match entre o preço do produto e o range de preço do alerta")
    public void shouldFailIfDontfindMatchBetweenProductPriceAndRangeAlert() {
        // Arrange
        PriceAlert priceAlert = new PriceAlert();
        Product product = new Product();
        priceAlert.setPriceRange(100.0);
        product.setPrice(BigDecimal.valueOf(150.0));

        // Act
        matchService.verifyMatches(priceAlert, product);

        //Assert
        verify(emailService, never()).sendMatchEmail(priceAlert, product);
    }
}