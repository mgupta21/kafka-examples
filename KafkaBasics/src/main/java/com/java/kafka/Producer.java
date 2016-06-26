package com.java.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class Producer {

    private Properties    kafkaProps = new Properties();
    private KafkaProducer producer;

    public void config() {
        kafkaProps.put("bootstrap.servers", "localhost:9092");

        // kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // kafkaProps.put("value.serializer", "com.java.kafka.CustomerSerializer");

        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<String, String>(kafkaProps);

    }

    public void start() {
        producer = new KafkaProducer<String, String>(kafkaProps);
    }

    public void send(String type) {
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

    public static void main(String[] args) {
        Producer p = new Producer();
        p.config();
        p.start();
        p.send("sync");
        // p.sendObj();

        Consumer.run();
    }

}
