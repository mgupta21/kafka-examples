package com.java.kafka;

import java.io.IOException;
import java.util.List;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class Producer extends KafkaBase {

    private KafkaProducer producer;
    private final String  PROP_FILE_NAME = "producer.properties";

    public void config() {
        try {
            loadProps(PROP_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        producer = new KafkaProducer<String, String>(kafkaProps);
    }

    public void send(List<ProducerRecord<String, String>> records) {
        for (ProducerRecord<String, String> record : records) {
            try {
                producer.send(record);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendWithOption(String type) {
        ProducerRecord<String, String> record = new ProducerRecord<>("Country", "Name", "France");
        try {
            if (type == "sync") {
                producer.send(record);
            } else if (type == "async_f") {
                producer.send(record).get();
            } else if (type == "async_c") {
                producer.send(record, new ProducerCallback());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendObj() {

        Customer customer = new Customer(01, "Mayank");
        ProducerRecord<Integer, Customer> record = new ProducerRecord<Integer, Customer>("country", customer.getID(), customer);
        try {
            producer.send(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ProducerCallback implements Callback {

        @Override
        public void onCompletion(RecordMetadata meta, Exception e) {
            if (e != null) {
                e.printStackTrace();
            }
        }
    }

}
