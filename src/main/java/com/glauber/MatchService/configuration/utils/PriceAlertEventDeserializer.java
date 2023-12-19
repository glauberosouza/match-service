package com.glauber.MatchService.configuration.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glauber.MatchService.domain.entity.PriceAlert;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class PriceAlertEventDeserializer implements Deserializer<PriceAlert> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PriceAlert deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, PriceAlert.class);
        } catch (IOException e) {
            throw new SerializationException("Erro ao desserializar a mensagem do Kafka", e);
        }
    }
}
