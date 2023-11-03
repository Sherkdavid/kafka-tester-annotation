package com.labday.kafkatester.util;

import java.nio.ByteBuffer;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.ByteBufferSerializer;
import org.apache.kafka.common.serialization.BytesSerializer;
import org.apache.kafka.common.serialization.DoubleSerializer;
import org.apache.kafka.common.serialization.FloatSerializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.ListSerializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.ShortSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.apache.kafka.common.serialization.VoidSerializer;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.kafka.support.serializer.JsonSerializer;


public class Serializers
{
    private static final Map<Class<?>, Serializer<?>> SERIALIZERS = Map.ofEntries(
        new AbstractMap.SimpleEntry<Class<?>, Serializer<?>>(byte[].class, new ByteArraySerializer()),
        new AbstractMap.SimpleEntry<Class<?>, Serializer<?>>(ByteBuffer.class, new ByteBufferSerializer()),
        new AbstractMap.SimpleEntry<Class<?>, Serializer<?>>(Bytes.class, new BytesSerializer()),
        new AbstractMap.SimpleEntry<Class<?>, Serializer<?>>(Double.class, new DoubleSerializer()),
        new AbstractMap.SimpleEntry<Class<?>, Serializer<?>>(Float.class, new FloatSerializer()),
        new AbstractMap.SimpleEntry<Class<?>, Serializer<?>>(Integer.class, new IntegerSerializer()),
        new AbstractMap.SimpleEntry<Class<?>, Serializer<?>>(List.class, new ListSerializer<>()),
        new AbstractMap.SimpleEntry<Class<?>, Serializer<?>>(Long.class, new LongSerializer()),
        new AbstractMap.SimpleEntry<Class<?>, Serializer<?>>(Short.class, new ShortSerializer()),
        new AbstractMap.SimpleEntry<Class<?>, Serializer<?>>(String.class, new StringSerializer()),
        new AbstractMap.SimpleEntry<Class<?>, Serializer<?>>(UUID.class, new UUIDSerializer()),
        new AbstractMap.SimpleEntry<Class<?>, Serializer<?>>(Void.class, new VoidSerializer())
    );

    public static Serializer<?> getSerializer(final Class<?> clazz)
    {
        if (SERIALIZERS.containsKey(clazz))
        {
            return SERIALIZERS.get(clazz);
        }
        final JsonSerializer<?> serializer = new JsonSerializer<>();
        serializer.setAddTypeInfo(false);
        return serializer;
    }

}
