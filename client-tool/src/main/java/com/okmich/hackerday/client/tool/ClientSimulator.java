/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.hackerday.client.tool;

import com.okmich.hackerday.client.tool.dashboard.kafka.KafkaMessageProducer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 *
 * @author m.enudi
 */
public class ClientSimulator implements Runnable {

    private final File file;
    private final String topic;
    private final KafkaMessageProducer kafkaMessageProducer;
    private final Random random;

    private static final Logger LOG = Logger.getLogger(ClientSimulator.class.getName() + Thread.currentThread().getName());

    /**
     *
     * @param file
     * @param brokerUrl
     * @param topic
     */
    public ClientSimulator(File file, String brokerUrl, String topic) {
        this.file = file;
        this.topic = topic;

        this.kafkaMessageProducer = new KafkaMessageProducer(brokerUrl);
        this.random = new Random(1234);
        LOG.log(Level.INFO, "Thread worker initialized to read from {0}", file);
    }

    @Override
    public void run() {
        //read file content
        LineIterator lIt;
        try {
            LOG.log(Level.INFO, "Reading the content of file");
            lIt = FileUtils.lineIterator(this.file);
        } catch (IOException ex) {
            Logger.getLogger(ClientSimulator.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
        String line;
        LOG.log(Level.INFO, "Sending file content to kafka topic - {0}", this.topic);
        while (lIt.hasNext()) {
            line = lIt.nextLine();
            //send message to kafka
            this.kafkaMessageProducer.send(this.topic, line);
            try {
                Thread.currentThread().sleep(this.random.nextInt(1000));
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientSimulator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
