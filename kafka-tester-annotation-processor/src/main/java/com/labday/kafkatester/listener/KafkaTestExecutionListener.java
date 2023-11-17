package com.labday.kafkatester.listener;

import org.apache.kafka.streams.TopologyTestDriver;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

@Order
public class KafkaTestExecutionListener implements TestExecutionListener
{
    /**
     * Close TopologyTestDriver closeable
     * @param testContext the test context for the test; never {@code null}
     * @throws Exception
     */
    @Override
    public void afterTestMethod(final TestContext testContext) throws Exception
    {
        final TopologyTestDriver topologyTestDriver = testContext.getApplicationContext().getBean(TopologyTestDriver.class);
        topologyTestDriver.close();
    }
}
