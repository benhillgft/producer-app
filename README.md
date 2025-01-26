# Producer-App

## Overview

`Producer-App` is a Java-based application designed to send messages to an IBM MQ message queue. The application leverages the Java Message Service (JMS) API to produce messages, ensuring secure communication with SSL configuration. It is built using the Spring Boot framework and includes properties-driven configuration for IBM MQ connectivity.

---

## Features

- Sends text messages to an IBM MQ message queue.
- Reads messages from a text file (`list_travelers.txt`) and publishes them to the queue at a defined interval.
- Configurable via external properties (`application.properties`).
- Secure connection with SSL/TLS support for IBM MQ.
- Built with Spring Boot for easy deployment and integration.

---

## Prerequisites

- Java 11 or later
- Maven 3.6+ for building the application
- IBM MQ Server instance with appropriate queue setup
- SSL TrustStore file (`truststore.jks`) for secure connections

---

## Configuration

Set the following properties in the `application.properties` file:

```properties
app.host=<IBM_MQ_HOST>
app.port=<IBM_MQ_PORT>
app.channel=<IBM_MQ_CHANNEL>
app.qmgr=<IBM_MQ_QUEUE_MANAGER>
app.queue=<QUEUE_NAME>
app.user=<IBM_MQ_USERNAME>
app.password=<IBM_MQ_PASSWORD>
