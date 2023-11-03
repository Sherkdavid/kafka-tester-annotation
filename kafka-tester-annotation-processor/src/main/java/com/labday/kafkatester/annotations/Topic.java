package com.labday.kafkatester.annotations;

public @interface Topic
{
    String topicName();

    Class<?> keyType();

    Class<?> valueType();
}
