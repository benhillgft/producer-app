## Application Overview
Producer-App is a Java-based application designed to send messages to an IBM MQ message queue. The application leverages the Java Message Service (JMS) API to produce messages, ensuring secure communication with SSL configuration. It is built using the Spring Boot framework and includes properties-driven configuration for IBM MQ connectivity.

### Key Features
- Sends text messages to an IBM MQ message queue.
- Reads messages from a text file (list_travelers.txt) and publishes them to the queue at a defined interval.
- Configurable via external properties (application.properties).
- Secure connection with SSL/TLS support for IBM MQ.
- Built with Spring Boot for easy deployment and integration.

## Refactoring Objectives:
You are an expert in Java development, specializing in refactoring legacy messaging systems from IBM MQ to Apache Kafka. Your task is to refactor the provided application code to use Kafka. Ensure the refactored code adheres to the following guidelines:
- Completely replace all IBM MQ-specific logic, configurations, and package imports with Kafka producer logic.
- Use Kafka's Producer API from the org.apache.kafka.clients.producer package.
- Remove any references to IBM MQ-specific classes, configuration properties, or package imports.

## Kafka-Specific Java Best Practices:
- Configure Kafka producer using Properties or a strongly typed configuration object with key settings like:
    - bootstrap.servers
    - key.serializer
    - value.serializer
    - acks
- Implement retry logic (retries) and enable idempotence for message delivery reliability.
- Manage the Kafka producer lifecycle using a try-with-resources block for automatic resource cleanup.
- Use asynchronous send operations with callbacks (Callback interface) to handle message acknowledgments and errors.
- Log message delivery success or failure using a logging framework like SLF4J.
- Parameterize topic names and other configurations to enhance code reusability and flexibility.
- Optimize for scalability and performance by adjusting producer configurations (e.g., batch.size and compression.type).

## Additional Guidelines:
- Clean Up Legacy Code:
    - Remove all IBM MQ-related imports, configurations, and references (e.g., connection factory, session management, or MQQueue).
- Refactor for Clean Code:
    - Refactor to separate concerns, such as configuration, message creation, and publishing.
    - Encapsulate producer logic in a reusable class or method.
- Adopt Java Best Practices:
    - Leverage dependency injection for testability and flexibility.
    - Optimize imports to include only the necessary classes and packages.
    - Use meaningful names for variables, methods, and classes to improve readability.
    - Add comprehensive JavaDoc comments and inline comments for clarity.
- Unit Testing:
    - Ensure the refactored code is testable, modular, and follows Single Responsibility Principle (SRP).
- Feature branches
    - When creating feature-branches, use the format feature/name-of-feature

## Expected Output:
The refactored code should be concise, clean, and adhere to the principles above. It must include:
- Parameterized configurations for flexibility.
- Proper error handling and logging.
- Removal of all IBM MQ-specific dependencies.
