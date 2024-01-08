package com.glauber.MatchService.model.service.impl;

import com.glauber.MatchService.model.entity.PriceAlert;
import com.glauber.MatchService.model.repository.PriceAlertRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceAlerServicetImplTest {
    @Mock
    private PriceAlertRepository priceAlertRepository;
    @InjectMocks
    private PriceAlerServicetImpl priceAlerServicetImpl;

    @Test
    @DisplayName("Deve encontrar um alerta pelo id")
    public void shouldFindAAlertById() {
        // Arrange
        var priceAlert = new PriceAlert(
                1L,
                "Glauber",
                "Xbox", 1000.0,
                "glauber@gmail.com");

        when(priceAlertRepository.findById(1L)).thenReturn(Optional.of(priceAlert));
        // Act
        var priceAlertById = priceAlerServicetImpl.findById(1L);
        //Assert
        assertEquals("Glauber", priceAlertById.getName());
        assertEquals("Xbox", priceAlertById.getProductName());
        assertEquals("glauber@gmail.com", priceAlertById.getEmail());
    }

    @Test
    @DisplayName("Deve deletar um alerta pelo id")
    public void shouldDeletePriceAlertById() {
        // Arrange
        var priceAlert = new PriceAlert(
                1L,
                "Glauber",
                "Xbox", 1000.0,
                "glauber@gmail.com");

        when(priceAlertRepository.findById(1L)).thenReturn(Optional.of(priceAlert));
        // Act
        priceAlerServicetImpl.delete(1L);
        var shouldBeEmpty = priceAlertRepository.findAll();
        //Assert
        assertEquals(0, shouldBeEmpty.size());

    }

}