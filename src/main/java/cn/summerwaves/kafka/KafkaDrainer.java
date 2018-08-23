package cn.summerwaves.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class KafkaDrainer {

    private String topic;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    private KafkaConsumer consumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "193.112.139.251:9092");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("group.id", "0");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.offset.reset", "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
        return consumer;
    }

    public static void main(String[] args) {
        String topic = "192.168.100.245";

        KafkaDrainer kafkaDrainer = new KafkaDrainer();
        kafkaDrainer.setTopic(topic);
        KafkaConsumer consumer = kafkaDrainer.consumer();


        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            System.out.println(records.count());
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.value());
//    	        consumer.seekToBeginning(new TopicPartition(record.topic(), record.partition()));
            }
        }
    }
}
