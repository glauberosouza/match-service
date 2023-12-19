package com.glauber.MatchService.configuration;

import com.glauber.MatchService.configuration.utils.PriceAlertEventDeserializer;
import com.glauber.MatchService.configuration.utils.ProductEventDeserializer;
import com.glauber.MatchService.domain.entity.PriceAlert;
import com.glauber.MatchService.domain.entity.Product;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
@Configuration
@EnableKafka
public class KafkaConsumerConfiguration {
    @Autowired
    private KafkaProperties kafkaProperties; // Configurar o Kafka.

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PriceAlert> containerFactoryAlert() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, PriceAlert>();
        factory.setConsumerFactory(consumerFactoryAlert());
        factory.setRecordMessageConverter(new JsonMessageConverter());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Product> containerFactoryProduct() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Product>();
        factory.setConsumerFactory(consumerFactoryProduct());
        factory.setRecordMessageConverter(new JsonMessageConverter());
        return factory;
    }

    private ConsumerFactory<String, PriceAlert> consumerFactoryAlert() {
        var config = new HashMap<String, Object>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group-alert");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PriceAlertEventDeserializer.class);
        //config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        Map<String, Object> props = kafkaProperties.buildConsumerProperties();
        props.putAll(config);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new PriceAlertEventDeserializer());
    }

    private ConsumerFactory<String, Product> consumerFactoryProduct() {
        var config = new HashMap<String, Object>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group-product");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ProductEventDeserializer.class);
        //config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        Map<String, Object> props = kafkaProperties.buildConsumerProperties();
        props.putAll(config);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new ProductEventDeserializer());
    }
}