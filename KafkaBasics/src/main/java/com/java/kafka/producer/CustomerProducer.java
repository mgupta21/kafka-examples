package com.java.kafka.producer;

import java.io.IOException;
import java.util.List;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

import com.java.kafka.KafkaBase;
import com.java.kafka.datamodel.Customer;

/**
 * Created by mgupta on 7/8/16.
 */
public class CustomerProducer extends KafkaBase {

    private KafkaProducer       producer;
    private final String        PROP_FILE_NAME = "producer.customer.properties";
    private static final Logger logger         = Logger.getLogger(CustomerProducer.class);

    public void config() {
        try {
            loadProps(PROP_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        producer = new KafkaProducer<String, String>(kafkaProps);
    }

    public void send(List<Customer> customers) {
        customers.forEach(c -> {
            logger.info("Sending customer with id : " + c.getCustomerId() + " and name : " + c.getCustomerName());
            ProducerRecord<String, Customer> record = new ProducerRecord<>(c.getCustomerName(), c);
            producer.send(record);
        });
    }

}
