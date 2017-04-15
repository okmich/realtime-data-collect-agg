/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.hackerday.client.tool.dashboard.kafka;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author m.enudi
 */
public class KafkaMessageProducer {

    private final KafkaProducer<String, String> kafkaProducer;

    public KafkaMessageProducer(String brokerUrl) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", brokerUrl);
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("metadata.fetch.timeout.ms", 1000);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        kafkaProducer = new KafkaProducer<>(properties);
    }

    /**
     *
     * @param topic
     * @param message
     */
    public void send(String topic, String message) {
        kafkaProducer.send(new ProducerRecord(topic, Long.toString(0), message));
        kafkaProducer.flush();
    }
}
