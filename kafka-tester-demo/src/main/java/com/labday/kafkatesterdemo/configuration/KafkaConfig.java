package com.labday.kafkatesterdemo.configuration;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;


@EnableKafkaStreams
@EnableKafka
@Configuration
public class KafkaConfig
{

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    KafkaStreamsConfiguration kStreamsConfig(final KafkaProperties kafkaProperties)
    {
        return new KafkaStreamsConfiguration(kafkaProperties.buildStreamsProperties());
    }
}
