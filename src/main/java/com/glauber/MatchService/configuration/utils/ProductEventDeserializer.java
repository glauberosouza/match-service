package com.glauber.MatchService.configuration.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glauber.MatchService.domain.entity.Product;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class ProductEventDeserializer implements Deserializer<Product> {
    private final ObjectMapper objectMapper = new ObjectMapper();
// TODO: VERIFICAR POR QUE ESTOU TENDO ERRO AO TENTAR DESERIALIZAR A MENSAGEM VINDA DO TÓPICO.
    @Override
    public Product deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, Product.class);
        } catch (IOException e) {
            throw new SerializationException("Erro ao desserializar a mensagem do Kafka", e);
        }
    }
}
