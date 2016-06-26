package com.java.kafka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * Created by mgupta on 6/25/16.
 */
public class Consumer {

    Properties                    props;
    KafkaConsumer<String, String> consumer;

    public void config() {
        props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "CountryCounter");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

    }

    public void start() {
        consumer = new KafkaConsumer<>(props);
        ArrayList<String> topics = new ArrayList<>();
        topics.add("Country");
        consumer.subscribe(topics);
    }

    public void consume() {
        try {
            //while (true) {
                ConsumerRecords<String, String> records = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format("topic = %s, partition = %s, offset = %d, customer = %s, country = %s\n",
                        record.topic(), record.partition(), record.offset(), record.key(), record.value()));

                    int updatedCount = 1;
                    Map<String, Integer> custCountryMap = new HashMap<>();
                    if (custCountryMap.containsValue(record.value())) {
                        updatedCount = custCountryMap.get(record.value()) + 1;
                    }
                    custCountryMap.put(record.value(), updatedCount);

                    System.out.println(custCountryMap.toString());
                }
            //}
        } finally {
            consumer.close();
        }
    }

    public static void run() {
        Consumer consumer = new Consumer();
        consumer.config();
        consumer.start();
        consumer.consume();
    }

}
