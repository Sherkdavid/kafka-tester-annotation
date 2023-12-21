### TODO

- [X] Create a simple demo Kafka Streams application
- [X] Generate a Topology using TopologyTestDriver via the @KafkaTest annotation
- [X] Intuit the type of Serde which must be used (Primitive Serdes or JsonSerde for complex types)
- [X] Use the ContextConfigurationCustomizer to add topics, input and output to the application context
- [X] Implement listener for closeable TopologyTestDriver
- [X] Write multiple test cases to see how the framework performs under repeated use
- [X] Clean up TODOs in the code
- [ ] Provide a means for the user to add their own Serializers and Deserializers to the framework
- [ ] Separate into;
    - [ ] kafka-test-annotation-spring
    - [ ] kafka-test-annotation-spring-boot
    - [ ] kafka-test-annotation-spring-boot-starter
- [ ] Prepare library for release

## Long-term goals

- Provide a means of creating test objects for external kafka cluster, integration test context