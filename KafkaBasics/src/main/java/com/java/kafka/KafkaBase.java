package com.java.kafka;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by mgupta on 6/25/16.
 */
public class KafkaBase {

    protected Properties kafkaProps = new Properties();

    protected void loadProps(String propFileName) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            kafkaProps.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
    }
}
