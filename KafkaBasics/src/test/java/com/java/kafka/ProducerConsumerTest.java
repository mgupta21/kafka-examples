package com.java.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Before;
import org.junit.Test;

import com.java.kafka.consumer.Consumer;
import com.java.kafka.producer.Producer;

/**
 * Created by mgupta on 6/25/16.
 */
public class ProducerConsumerTest {

    private static final List<String> COUNTRY_NAMES = Arrays.asList("United Kingdom", "France", "Australia", "India", "United States", "Sweden");
    private static final String       TOPIC         = "Country";

    private List<ProducerRecord<String, String>> getRecordsForTopic(String topic) {
        List<ProducerRecord<String, String>> records = new ArrayList<>();
        COUNTRY_NAMES.forEach(c -> records.add(new ProducerRecord<>(topic, c)));
        return records;
    }

    public Producer producer;
    public Consumer consumer;

    @Before
    public void setup() {
        producer = new Producer();
        producer.config();
        consumer = new Consumer();
        consumer.config(TOPIC);
    }

    @Test
    public void testSendAndReceive() {
        producer.send(getRecordsForTopic(TOPIC));
        consumer.consume();
    }

    @Test
    public void testSynchronousSendAndReceive() {
        producer.sendSynchronously(getRecordsForTopic(TOPIC));
        consumer.consume();
    }

    @Test
    public void testASynchronousSendAndReceive() {
        producer.sendASynchronously(getRecordsForTopic(TOPIC));
        consumer.consume();
    }

}
