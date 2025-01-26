package com.gft.mqtransition.producer;

import com.gft.mqtransition.MqtransitionApplication;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.jms.*;
import javax.jms.JMSProducer;
import java.io.*;

@Component
public class Producer {

    private static final Logger log = LoggerFactory.getLogger(Producer.class);
    JMSContext context = null;
    Destination destination = null;
    JMSProducer producer = null;

    public Producer(@Autowired AppConfig applicationProperties) throws IOException {
        // Set up SSL truststore
        String trustStoreFileName = "/truststore.jks"; // Path relative to the classpath
        InputStream trustStoreStream = Producer.class.getResourceAsStream(trustStoreFileName);

        if (trustStoreStream == null) {
            throw new RuntimeException("TrustStore file not found in resources: " + trustStoreFileName);
        }

        // Copy the .jks file to a temporary location
        File tempTrustStore = File.createTempFile("truststore", ".jks");
        try (FileOutputStream fos = new FileOutputStream(tempTrustStore)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = trustStoreStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Set the trustStore system properties
        System.setProperty("javax.net.ssl.trustStore", tempTrustStore.getAbsolutePath());
        System.setProperty("javax.net.ssl.trustStorePassword", "2fade2dade");

        try {
            // Create a connection factory
            JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
            JmsConnectionFactory cf = ff.createConnectionFactory();

            // Set the properties
            cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, applicationProperties.getHOST());
            cf.setIntProperty(WMQConstants.WMQ_PORT, applicationProperties.getPORT());
            cf.setStringProperty(WMQConstants.WMQ_CHANNEL, applicationProperties.getCHANNEL());
            cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
            cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, applicationProperties.getQMGR());
            cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "JMS_IBMMQ_Producer");
            cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
            cf.setStringProperty(WMQConstants.USERID, applicationProperties.getAPP_USER());
            cf.setStringProperty(WMQConstants.PASSWORD, applicationProperties.getAPP_PASSWORD());
            cf.setStringProperty(WMQConstants.WMQ_SSL_CIPHER_SUITE, "*TLS12ORHIGHER");

            // Create JMS objects
            context = cf.createContext();
            destination = context.createQueue("queue:///" + applicationProperties.getQUEUE_NAME());
            this.producer = context.createProducer();
        } catch (Exception jmsError) {
            jmsError.printStackTrace();
        }
    }

    public void produce(String line) {
        TextMessage message = context.createTextMessage(line);
        this.producer.send(destination, message);
        log.info("Sent message:\n" + message);

    }
}


