/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.hackerday.client.tool.dashboard.kafka;

import com.okmich.hackerday.client.tool.dashboard.Handler;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private static final Logger LOG = Logger.getLogger(KafkaMessageConsumer.class.getName());

    private Executor executor;

    /**
     *
     * @param topic
     * @param ihandlerMap
     */
    public KafkaMessageConsumer(String topic, Map<String, Handler> ihandlerMap) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.8.120:9092");
        props.put("group.id", "RealTimeSystemDashboard");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        kafkaConsumer = new KafkaConsumer<>(props);
        LOG.log(Level.INFO, "Subscribing to kafka topic {0}", topic);
        kafkaConsumer.subscribe(Arrays.asList(topic));

        this.handlerMap = ihandlerMap;

        executor = Executors.newFixedThreadPool(2);
    }

    /**
     *
     */
    public void start() {
        String parts[];
        LOG.log(Level.INFO, "Listening for messages ...");
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                parts = record.value().split("=");
                executor.execute(() -> {
                    handlerMap.keySet().stream().forEach((key) -> {
                        try {
                            handlerMap.get(key).handle("");
                        } catch (Exception ex) {
                            LOG.log(Level.SEVERE, ex.getMessage(), ex);
                        }
                    });
                });
            }
        }
    }

//    public static void main(String[] args) {
//        new KafkaMessageConsumer("result-aggregation-topic", null).start();
//    }
}
