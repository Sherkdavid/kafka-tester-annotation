package com.labday.kafkatesterdemo.builder;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.labday.kafkatesterdemo.domain.TestKey;
import com.labday.kafkatesterdemo.domain.TestValue;


@Configuration
public class DemoStreamsBuilder
{
    @Autowired
    public void buildStream(final StreamsBuilder streamsBuilder)
    {
        streamsBuilder.stream("input", Consumed.with(new JsonSerde<>(TestKey.class), new JsonSerde<>(TestValue.class)))
            .peek((key, value) -> System.out.println(value))
            .to("output", Produced.with(new JsonSerde<>(TestKey.class), new JsonSerde<>(TestValue.class)));
    }
}
