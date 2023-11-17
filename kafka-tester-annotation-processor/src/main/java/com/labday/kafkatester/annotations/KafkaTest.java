package com.labday.kafkatester.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.TestExecutionListeners;

import com.labday.kafkatester.listener.KafkaTestExecutionListener;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@TestExecutionListeners(value = KafkaTestExecutionListener.class, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public @interface KafkaTest
{
    Class<?> streamsBuilderClass();

    Topic[] inputTopics();

    Topic[] outputTopics();
}
