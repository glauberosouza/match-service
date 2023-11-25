package com.glauber.MatchService.configuration;

import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.JsonMessageConverter;

import java.util.HashMap;

@Configuration
public class KafkaConsumerConfiguration {
    @Autowired
    private KafkaProperties kafkaProperties; // Configurar o Kafka.

    private ConsumerFactory<String, Object> consumerFactoryAlert(){
        var config = new HashMap<String, Object>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group-alert");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(config);
    }

    private ConsumerFactory<String, Object> consumerFactoryProduct(){
        var config = new HashMap<String, Object>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group-product");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> containerFactoryAlert(){
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
        factory.setConsumerFactory(consumerFactoryAlert());
        factory.setRecordMessageConverter(new JsonMessageConverter());
        return factory;
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> containerFactoryProduct(){
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
        factory.setConsumerFactory(consumerFactoryProduct());
        factory.setRecordMessageConverter(new JsonMessageConverter());
        return factory;
    }

}