package com.labday.kafkatester.customizer;

import static com.labday.kafkatester.util.Deserializers.getDeserializer;
import static com.labday.kafkatester.util.Serializers.getSerializer;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.ByteBufferDeserializer;
import org.apache.kafka.common.serialization.ByteBufferSerializer;
import org.apache.kafka.common.serialization.BytesDeserializer;
import org.apache.kafka.common.serialization.BytesSerializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.DoubleDeserializer;
import org.apache.kafka.common.serialization.DoubleSerializer;
import org.apache.kafka.common.serialization.FloatDeserializer;
import org.apache.kafka.common.serialization.FloatSerializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.ListDeserializer;
import org.apache.kafka.common.serialization.ListSerializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.ShortDeserializer;
import org.apache.kafka.common.serialization.ShortSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.apache.kafka.common.serialization.VoidDeserializer;
import org.apache.kafka.common.serialization.VoidSerializer;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.TopologyTestDriver;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.util.ReflectionUtils;

import com.labday.kafkatester.annotations.KafkaTest;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class KafkaTestContextCustomizer implements ContextCustomizer
{
    private final KafkaTest kafkaTest;
    private TopologyTestDriver topologyTestDriver;
    private static final String CUSTOMIZER_PREFIX = "KafkaTestContextCustomizer";

    public KafkaTestContextCustomizer(final KafkaTest kafkaTest)
    {
        this.kafkaTest = kafkaTest;
    }

    @SneakyThrows
    @Override
    public void customizeContext(final ConfigurableApplicationContext context, final MergedContextConfiguration mergedConfig)
    {
        final StreamsBuilder streamsBuilder = new StreamsBuilder();
        final Class<?> clazz = kafkaTest.streamsBuilderClass();
        final Object streamsBuilderClass = context.getBeanFactory().getBean(clazz);
        final Optional<Method> builderMethodOptional = Arrays.stream(ReflectionUtils.getDeclaredMethods(clazz))
            .filter(method -> method.getParameters().length == 1 && method.getParameters()[0].getType().isAssignableFrom(StreamsBuilder.class))
            .findFirst();
        final Method builderMethod = builderMethodOptional.orElseThrow(
            () -> new UnsupportedOperationException(String.format("%s must provide one method accepting a StreamsBuilder parameter", clazz.getName()))
        );
        ReflectionUtils.invokeMethod(builderMethod, streamsBuilderClass, streamsBuilder);
        topologyTestDriver = new TopologyTestDriver(streamsBuilder.build());
        context.getBeanFactory().registerSingleton("topologyTestDriver", topologyTestDriver);
        Arrays.stream(kafkaTest.inputTopics())
            .map(topic -> topologyTestDriver.createInputTopic(topic.topicName(),
                getSerializer(topic.keyType()),
                getSerializer(topic.valueType())))
            .forEach(testInputTopic -> {
                final String beanName = CUSTOMIZER_PREFIX + "_" + testInputTopic;
                context.getBeanFactory().initializeBean(testInputTopic, beanName);
                context.getBeanFactory().registerSingleton(beanName, testInputTopic);
            });

        Arrays.stream(kafkaTest.outputTopics())
            .map(topic -> topologyTestDriver.createOutputTopic(topic.topicName(),
                getDeserializer(topic.keyType()),
                getDeserializer(topic.valueType())))
            .forEach(testOutputTopic -> {
                final String beanName = CUSTOMIZER_PREFIX + "_" + testOutputTopic;
                context.getBeanFactory().initializeBean(testOutputTopic, beanName);
                context.getBeanFactory().registerSingleton(beanName, testOutputTopic);
            });
    }
}
