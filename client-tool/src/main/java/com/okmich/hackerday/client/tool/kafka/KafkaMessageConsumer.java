/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.hackerday.client.tool.kafka;

import com.okmich.hackerday.client.tool.dashboard.Handler;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 *
 * @author m.enudi
 */
public class KafkaMessageConsumer {

    private final KafkaConsumer<String, String> kafkaConsumer;

    private final Map<String, Handler> handlerMap;

    /**
     *
     * @param topic
     * @param ihandlerMap
     */
    public KafkaMessageConsumer(String topic, Map<String, Handler> ihandlerMap) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.8.120:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Arrays.asList(topic));

        this.handlerMap = ihandlerMap;
    }

    /**
     *
     */
    public void start() {
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
            }
        }
    }
}
