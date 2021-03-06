package com.java.kafka.producer;

import java.io.IOException;
import java.util.List;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;

import com.java.kafka.KafkaBase;

/**
 * Created by mgupta on 6/25/16.
 */
public class Producer extends KafkaBase {

    private KafkaProducer       producer;
    private final String        PROP_FILE_NAME = "producer.properties";
    private static final Logger logger         = Logger.getLogger(Producer.class);

    public void config() {
        try {
            loadProps(PROP_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        producer = new KafkaProducer<String, String>(kafkaProps);
    }

    /* Fire and Forget : producer retry's to send the message but delivery is not guaranteed */
    public void send(List<ProducerRecord<String, String>> records) {
        // Single producer can be used to send messages by multiple threads
        // Send returns future object
        records.forEach(r -> {
            producer.send(r);
            logger.info(String.format("Sending record : {%s, %s}", r.key(), r.value()));
        });
    }

    /* send synchronously */
    public void sendSynchronously(List<ProducerRecord<String, String>> records) {
        records.forEach(r -> {
            try {
                // Future.get() to wait until reply from kafka arrives
                producer.send(r).get();
            } catch (Exception e) {
                logger.error("Error while sending message synchronously : " + e.getMessage());
            }
        });
    }

    /* send Asynchronously */
    public void sendASynchronously(List<ProducerRecord<String, String>> records) {
        records.forEach(r -> producer.send(r, new ProducerCallback()));
    }

    private class ProducerCallback implements Callback {

        private final Logger logger = Logger.getLogger(ProducerCallback.class);

        @Override
        public void onCompletion(RecordMetadata meta, Exception e) {
            if (e != null) {
                logger.error("Error while sending message Asynchronously : " + e.getMessage());
            }
        }
    }

}