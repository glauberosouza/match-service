package com.glauber.MatchService.listeners;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glauber.MatchService.model.entity.PriceAlert;
import com.glauber.MatchService.model.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PriceAlertListener {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmailService emailService;

    @KafkaListener(
            topics = "NEW_PRICE_ALERT", groupId = "new-price-alert"
    )
    public void messageListener(String message) {
        try {
            var priceAlertEvent = objectMapper.readValue(message, PriceAlert.class);
            log.info("Novo alerta de preço recebido do Kafka: " + priceAlertEvent);
            emailService.sendAlertConfirmation(priceAlertEvent);

        } catch (JsonProcessingException e) {
            log.error("Ocorreu uma falha ao desserializar o evento JSON: " + e.getMessage());
        } catch (Exception e) {
            log.error("Ocorreu uma falha ao processar o evento do Kafka: " + e.getMessage());
        }
    }
}