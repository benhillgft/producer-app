package com.gft.mqtransition;


import com.gft.mqtransition.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;

import javax.jms.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
public class MqtransitionApplication{

	private static final Logger log = LoggerFactory.getLogger(MqtransitionApplication.class);

	private static Producer producer;



	public MqtransitionApplication(Producer producer) {
		this.producer = producer;
	}

	public static void main(String[] args) throws JMSException, jakarta.jms.JMSException, IOException {

		SpringApplication.run(MqtransitionApplication.class, args);

		log.info("Reading messages from file");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					MqtransitionApplication.class.getClassLoader().getResourceAsStream("list_travelers.txt")));
			String line = reader.readLine();
			while (line != null) {
				producer.produce(line);
				Thread.sleep(2000);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			log.error("Error reading file: {}", e.getMessage());
			throw e;
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		log.info("Finished reading messages from file");
	}



}
