package com.glauber.MatchService.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glauber.MatchService.model.entity.Product;
import com.glauber.MatchService.model.repository.ProductRepository;
import com.glauber.MatchService.model.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
//TODO VERIFICAR O PORQUE NÃO ESTÁ SENDO ENVIADO EMAIL INFORMANDO QUE O PREÇO ESTÁ NO RANGE DO CLIENTE
@Slf4j
@Component
public class ProductListener {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EmailService emailService;

    @KafkaListener(
            topics = "NEW_PRODUCT", groupId = "new-product"
    )
    public void messageListener(String message) {
        try {
            var productEvent = objectMapper.readValue(message, Product.class);
            log.info("Novo produto redebido do Kafka: " + productEvent);
            productRepository.save(productEvent);
            emailService.sendMatchEmail(productEvent);

        } catch (JsonProcessingException e) {
            log.error("Ocorreu uma falha ao desserializar o evento JSON: " + e.getMessage());
        } catch (Exception e) {
            log.error("Ocorreu uma falha ao processar o evento do Kafka: " + e.getMessage());
        }
    }
}