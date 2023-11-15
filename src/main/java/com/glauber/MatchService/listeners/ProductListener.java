package com.glauber.MatchService.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glauber.MatchService.domain.entity.ProductEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
// TODO: EU PRECISO ENCONTRAR UMA FORMA DE PEGAR OS DADOS VINDOS NA MENSAGEM E CONVERTE-LOS PARA O MEU PRODUCTEVENT.
// TODO: ESTOU TENTANDO UTILIZA O OBJECTMAPPER PARA DE CERTA FORMA ATRIBUIR NO PRODUCTEVENT AS INFORMAÇÔES QUE VIEREM NA MENSAGEM

@Log4j2
@Component
public class ProductListener {

    @Autowired
    private ObjectMapper objectMapper;
    @KafkaListener(
            topics = "PRODUCT_TOPIC",
            groupId = "group-product",
            containerFactory = "containerFactory"
            // Aponta para o Kafka factory configurado no bean da classe KafkaConfiguration
    )
    public void messageListener(String message) {
        try {
            var productEvent = objectMapper.readValue(message, ProductEvent.class);
            log.info(":::Novo produto redebido do Kafka: " + productEvent);
        }catch (Exception e){
            log.error("Erro ao desserializar o evento: " + e.getMessage());
        }

    }
}