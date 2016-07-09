package com.java.kafka;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Before;
import org.junit.Test;

import com.java.kafka.consumer.Consumer;
import com.java.kafka.producer.Producer;

/**
 * Created by mgupta on 6/25/16.
 */
public class ProducerConsumerTest {

    private static final String        TOPIC = "Country";
    private static Map<String, String> testMap;

    {
        Map<String, String> map = new HashMap<>();
        map.put("Joe", "United Kingdom");
        map.put("Doe", "France");
        map.put("Amy", "France");
        map.put("Robert", "India");
        map.put("Shane", "Australia");
        map.put("Kira", "Australia");
        testMap = Collections.unmodifiableMap(map);
    }

    private List<ProducerRecord<String, String>> getRecordsForTopic(String topic) {
        List<ProducerRecord<String, String>> records = new ArrayList<>();
        testMap.forEach((k, v) -> records.add(new ProducerRecord<>(topic, k, v)));
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
