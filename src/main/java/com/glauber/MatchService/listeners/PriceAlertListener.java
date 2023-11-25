package com.glauber.MatchService.listeners;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.glauber.MatchService.domain.entity.PriceAlertEvent;
import com.glauber.MatchService.service.EmailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

// TODO: EU PRECISO ENCONTRAR UMA FORMA DE PEGAR OS DADOS VINDOS NA MENSAGEM E CONVERTE-LOS PARA O MEU PRICEALERTEVENT.
// TODO: ESTOU TENTANDO UTILIZAR O OBJECTMAPPER PARA DE CERTA FORMA ATRIBUIR NO PRICEALERTEVENT AS INFORMAÇÔES QUE CHEGAREM DO TÓPICO
@Log4j2
@Component
public class PriceAlertListener {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmailService emailService;

    @KafkaListener(
            topics = "NEW_PRICE_ALERT",
            groupId = "group-alert",
            containerFactory = "containerFactoryAlert" // Aponta para o Kafka factory configurado no bean da classe KafkaConfiguration
    )
    public void messageListener(String message) {
        try {
            var priceAlertEvent = objectMapper.readValue(message, PriceAlertEvent.class);
            log.info("Novo alerta de preço recebido do Kafka: " + priceAlertEvent);
            emailService.sendAlertConfirmation(message);

        } catch (Exception e) {
            log.error("Ocorreu uma falha ao desserializar o evento: " + e.getMessage());

        }
    }
}