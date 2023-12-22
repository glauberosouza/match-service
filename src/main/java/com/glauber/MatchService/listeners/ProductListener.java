package com.glauber.MatchService.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glauber.MatchService.model.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
// TODO: EU PRECISO ENCONTRAR UMA FORMA DE PEGAR OS DADOS VINDOS NA MENSAGEM E CONVERTE-LOS PARA O MEU PRODUCTEVENT.
// TODO: ESTOU TENTANDO UTILIZAR O OBJECTMAPPER PARA DE CERTA FORMA ATRIBUIR NO PRODUCTEVENT AS INFORMAÇÔES QUE CHEGAREM DO TÓPICO

@Slf4j
@Component
public class ProductListener {

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(
            topics = "NEW_PRODUCT", groupId = "new-product"
    )
    public void messageListener(String message) {
        try {
            var productEvent = objectMapper.readValue(message, Product.class);
            log.info("Novo produto redebido do Kafka: " + productEvent);
        } catch (JsonProcessingException e) {
            log.error("Ocorreu uma falha ao desserializar o evento JSON: " + e.getMessage());
        } catch (Exception e) {
            log.error("Ocorreu uma falha ao processar o evento do Kafka: " + e.getMessage());
        }
    }
}