# Install sudo pip install git+git://github.com/mumrah/kafka-python
# Ensure zookeeper server and kafka server are started before executing

from kafka import (
    KafkaClient,
    SimpleProducer,
    SimpleConsumer
)

# Python kafka client do not have dependency on zookeeper.
kafka = KafkaClient('localhost:9092')

# producer = SimpleProducer(kafka, async=True)
producer = SimpleProducer(kafka) # default sync=True, sends response back
response = producer.send_messages('pytopic', 'hello python!', 'hello again!')
print(response[0].offset)

consumer = SimpleConsumer(kafka, 'pygroup', 'pytopic')
for msg in consumer:
    print("Received Message : " + str(msg))

kafka.close()
