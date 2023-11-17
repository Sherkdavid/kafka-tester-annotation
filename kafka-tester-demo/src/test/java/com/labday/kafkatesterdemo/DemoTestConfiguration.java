package com.labday.kafkatesterdemo;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.labday.kafkatesterdemo.builder.DemoStreamsBuilder;


@TestConfiguration
public class DemoTestConfiguration
{
    @Bean
    DemoStreamsBuilder demoStreamsBuilder()
    {
        return new DemoStreamsBuilder();
    }
}
