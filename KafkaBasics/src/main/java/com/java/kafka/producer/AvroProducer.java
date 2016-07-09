package com.java.kafka.producer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.java.kafka.KafkaBase;

/**
 * Created by mgupta on 7/9/16.
 **/
public class AvroProducer extends KafkaBase {

    Producer<String, GenericRecord> producer;
    private final String            PROP_FILE_NAME        = "producer.avro.properties";
    private final String            AVRO_SCHEMA_FILE_NAME = "message.avro.json";
    private static final Logger     logger                = Logger.getLogger(AvroProducer.class);

    public void config() {
        try {
            loadProps(PROP_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        producer = new KafkaProducer<>(kafkaProps);
    }

    public void send() {
        String schemaString = getSchema();
        Schema schema = new Schema.Parser().parse(schemaString);

        for (int nCustomers = 0; nCustomers < 10; nCustomers++) {
            String name = "exampleCustomer" + nCustomers;
            String email = "example " + nCustomers + "@example.com";
            GenericRecord customer = new GenericData.Record(schema);
            customer.put("id", nCustomers);
            customer.put("name", name);
            customer.put("email", email);
            ProducerRecord<String, GenericRecord> data = new ProducerRecord<>("customerContacts", name, customer);
            logger.info("Sending message : " + customer.toString());
            producer.send(data);
        }
    }

    private String getSchema() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(AVRO_SCHEMA_FILE_NAME).getFile());
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject.toString();
    }

    public static void main(String[] args) {
        // TODO : Fix this
        AvroProducer producer = new AvroProducer();
        producer.config();
        producer.send();
    }

}
