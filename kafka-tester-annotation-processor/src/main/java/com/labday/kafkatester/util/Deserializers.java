package com.labday.kafkatester.util;

import java.nio.ByteBuffer;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteBufferDeserializer;
import org.apache.kafka.common.serialization.BytesDeserializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.DoubleDeserializer;
import org.apache.kafka.common.serialization.FloatDeserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.ListDeserializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.ShortDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.apache.kafka.common.serialization.VoidDeserializer;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.kafka.support.serializer.JsonDeserializer;


public class Deserializers
{
    private static final Map<Class<?>, Deserializer<?>> deserializers = Map.ofEntries(
        new AbstractMap.SimpleEntry<Class<?>, Deserializer<?>>(byte[].class, new ByteArrayDeserializer()),
        new AbstractMap.SimpleEntry<Class<?>, Deserializer<?>>(ByteBuffer.class, new ByteBufferDeserializer()),
        new AbstractMap.SimpleEntry<Class<?>, Deserializer<?>>(Bytes.class, new BytesDeserializer()),
        new AbstractMap.SimpleEntry<Class<?>, Deserializer<?>>(Double.class, new DoubleDeserializer()),
        new AbstractMap.SimpleEntry<Class<?>, Deserializer<?>>(Float.class, new FloatDeserializer()),
        new AbstractMap.SimpleEntry<Class<?>, Deserializer<?>>(Integer.class, new IntegerDeserializer()),
        new AbstractMap.SimpleEntry<Class<?>, Deserializer<?>>(List.class, new ListDeserializer<>()),
        new AbstractMap.SimpleEntry<Class<?>, Deserializer<?>>(Long.class, new LongDeserializer()),
        new AbstractMap.SimpleEntry<Class<?>, Deserializer<?>>(Short.class, new ShortDeserializer()),
        new AbstractMap.SimpleEntry<Class<?>, Deserializer<?>>(String.class, new StringDeserializer()),
        new AbstractMap.SimpleEntry<Class<?>, Deserializer<?>>(UUID.class, new UUIDDeserializer()),
        new AbstractMap.SimpleEntry<Class<?>, Deserializer<?>>(Void.class, new VoidDeserializer())
    );

    public static Deserializer<?> getDeserializer(final Class<?> clazz)
    {
        if (deserializers.containsKey(clazz))
        {
            return deserializers.get(clazz);
        }
        final JsonDeserializer<?> deserializer = new JsonDeserializer<>(clazz);
        deserializer.setUseTypeHeaders(false);
        return deserializer;
    }
}
