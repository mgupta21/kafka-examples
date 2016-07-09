***Starting/Running kafka (from cmd line)***

# Start zookeeper (& to run the program in background)
/usr/local/kafka/bin/zookeeper-server-start.sh /usr/local/kafka/config/zookeeper.properties &

# Start kafka brokers
/usr/local/kafka/bin/kafka-server-start.sh /usr/local/kafka/config/server.properties &
/usr/local/kafka/bin/kafka-server-start.sh /usr/local/kafka/config/server2.properties &

# verify kaka servers
ps -ef | grep kafka

# create kafka topic (run bin/kafka-topics.sh to see all commands)
/usr/local/kafka/bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic first --partitions 2 --replication-factor 2

# describe topic
/usr/local/kafka/bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic first

# create porducer (to write/publish to a topic)
/usr/local/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic first

# create consumer (to read from a topic)
/usr/local/kafka/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic first

# Start Schema Registry (for Avro)
/usr/local/kafka/bin/schema-registry-start /usr/local/kafka/config/schema-registry.properties