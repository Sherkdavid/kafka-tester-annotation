package com.labday.kafkatester.customizer;

import java.util.List;

import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;
import org.springframework.test.context.TestContextAnnotationUtils;

import com.labday.kafkatester.annotations.KafkaTest;

import lombok.NonNull;


public class KafkaTestCustomizerFactory implements ContextCustomizerFactory
{
    @Override
    public ContextCustomizer createContextCustomizer(@NonNull final Class<?> testClass,
                                                     @NonNull final List<ContextConfigurationAttributes> configAttributes)
    {
        final KafkaTest kafkaTest = TestContextAnnotationUtils.findMergedAnnotation(testClass, KafkaTest.class);
        return kafkaTest != null ? new KafkaTestContextCustomizer(kafkaTest) : null;
    }
}
