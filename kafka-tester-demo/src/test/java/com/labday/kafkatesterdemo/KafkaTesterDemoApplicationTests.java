package com.labday.kafkatesterdemo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.test.TestRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.labday.kafkatester.annotations.KafkaTest;
import com.labday.kafkatester.annotations.Topic;
import com.labday.kafkatesterdemo.builder.DemoStreamsBuilder;
import com.labday.kafkatesterdemo.domain.TestKey;
import com.labday.kafkatesterdemo.domain.TestValue;


@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@KafkaTest(streamsBuilderClass = DemoStreamsBuilder.class,
    inputTopics = {
        @Topic(topicName = "input", keyType = TestKey.class, valueType = TestValue.class),
    },
    outputTopics = {
        @Topic(topicName = "output", keyType = TestKey.class, valueType = TestValue.class)
    })
@ContextConfiguration(classes = DemoStreamsBuilder.class)
class KafkaTesterDemoApplicationTests
{
    @Autowired
    TestInputTopic<TestKey, TestValue> inputTopic;

    @Autowired
    TestOutputTopic<TestKey, TestValue> outputTopic;

    @Test
    void test1()
    {
        final TestKey key = new TestKey("firstName", "lastName");
        final TestValue value = new TestValue("value");
        inputTopic.pipeInput(key, value);

        final List<TestRecord<TestKey, TestValue>> testRecords = outputTopic.readRecordsToList();
        assertThat(testRecords).hasSize(1);
        final TestKey keyResult = testRecords.get(0).getKey();
        final TestValue valueResult = testRecords.get(0).getValue();
        assertThat(keyResult.getFirstName()).isEqualTo(key.getFirstName());
        assertThat(keyResult.getSurname()).isEqualTo(key.getSurname());
        assertThat(valueResult.getValue()).isEqualTo(value.getValue());
    }

    @Test
    void testNullKey()
    {
        final TestValue value = new TestValue("value");
        inputTopic.pipeInput(null, value);

        final List<TestRecord<TestKey, TestValue>> testRecords = outputTopic.readRecordsToList();
        assertThat(testRecords).hasSize(1);
        final TestKey keyResult = testRecords.get(0).getKey();
        final TestValue valueResult = testRecords.get(0).getValue();
        assertThat(keyResult).isNull();
        assertThat(valueResult.getValue()).isEqualTo(value.getValue());
    }
}
