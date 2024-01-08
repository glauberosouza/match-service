package com.glauber.MatchService.model.service.impl;

import com.glauber.MatchService.model.entity.Product;
import com.glauber.MatchService.model.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("Deve encontrar um alerta pelo id")
    public void shouldFindAAlertById() {
        // Arrange
        var product = new Product(
                1L,
                "Nintendo",
                "www.store.com.br/nintendo",
                BigDecimal.valueOf(1000.0));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        // Act
        Product productById = productService.findById(1L);
        //Assert
        assertEquals("Nintendo", productById.getName());
        assertEquals("www.store.com.br/nintendo", productById.getLink());
        assertEquals(1000.0, productById.getPrice().doubleValue());
    }

    @Test
    @DisplayName("Deve deletar um alerta pelo id")
    public void shouldDeletePriceAlertById() {
        // Arrange
        var product = new Product(
                1L,
                "Nintendo",
                "www.store.com.br/nintendo",
                BigDecimal.valueOf(1000.0));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        // Act
        productService.delete(1L);
        var shouldBeEmpty = productRepository.findAll();
        //Assert
        assertEquals(0, shouldBeEmpty.size());
    }
}