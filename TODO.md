The outcome of this labday project is to create unit test infrastructure for Kafka Streams projects
via annotation processing. Using ContextCustomizers and some java reflection, we will create the
TopologyTestDriver, InputTopics and OutputTopics to facilitate quick generation of KafkaStreams unit tests

### TODO

- [X] Create a simple demo Kafka Streams application
- [X] Generate a Topology using TopologyTestDriver via the @KafkaTest annotation
- [X] Intuit the type of Serde which must be used (Primitive Serdes or JsonSerde for complex types)
- [X] Use the ContextConfigurationCustomizer to add topics, input and output to the application context
- [X] Implement listener for closeable TopologyTestDriver
- [X] Write multiple test cases to see how the framework performs under repeated use
- [X] Clean up TODOs in the code
- [ ] Provide a means for the user to add their own Serializers and Deserializers to the framework
- [ ] Prepare library for release on github