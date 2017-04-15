/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.hackerday.client.tool;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m.enudi
 */
public class RealTimeDeviceClientSimulator {

    private static final Logger LOG = Logger.getLogger(RealTimeDeviceClientSimulator.class.getName());

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("General argument: <data_dir> <broker_url> <topic>");
            System.exit(-1);
        }
//        args = new String[]{"F:\\data_dump\\Geolife Trajectories 1.3\\Data", "192.168.8.120:9092", "test_topic"};
        File[] files = new File(args[0]).listFiles((File dir, String name) -> name.contains(".pltdata"));

        LOG.log(Level.INFO, "Files found \n{0}", Arrays.asList(files));
        int noThreads = files.length;
        LOG.log(Level.INFO, "setting the number of concurrent threads to {0}", noThreads);
        Executor executor = Executors.newFixedThreadPool(noThreads);
        for (File file : files) {
            executor.execute(new ClientSimulator(file, args[1], args[2]));
        }
    }

}
