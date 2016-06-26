package com.java.kafka;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * Created by mgupta on 6/25/16.
 */
public class Consumer extends KafkaBase {

    KafkaConsumer<String, String> consumer;
    private final String          PROP_FILE_NAME = "consumer.properties";

    public void config(String... topics) {

        try {
            loadProps(PROP_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }

        consumer = new KafkaConsumer<>(kafkaProps);
        consumer.subscribe(Arrays.asList(topics));
    }

    public void consume() {
        try {
            // while (true) {
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
            // }
        } finally {
            consumer.close();
        }
    }

}
