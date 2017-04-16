/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.hackerday.client.tool.dashboard;

import static com.okmich.hackerday.client.tool.dashboard.Handler.*;
import com.okmich.hackerday.client.tool.dashboard.kafka.KafkaMessageConsumer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JFrame;

/**
 *
 * @author datadev
 */
public final class Dashboard {

    private final static String CONSUMER_TOPIC = "result-aggregation-topic";

    private final ReportItemPanel trajByDistancePanel;
    private final ReportItemPanel trajByDurationPanel;
    private final ReportItemPanel userByPeriodPanel;
    private final ReportItemPanel userByTrajPanel;

    private final Handler trajByDistanceHandler;
    private final Handler trajByDurationHandler;
    private final Handler userByPeriodHandler;
    private final Handler userByTrajHandler;

    private final Map<String, Handler> handlerMap;
    private final Map<String, ReportItemPanel> panelMap;

    private final JFrame clientDashboardFrame;

    private final KafkaMessageConsumer kafkaMessageConsumer;

    Dashboard() {
        trajByDistanceHandler = new TrajByDistanceHandler();
        trajByDurationHandler = new TrajByDurationHandler();
        userByPeriodHandler = new UserByPeriodHandler();
        userByTrajHandler = new UserByTrajectoryHandler();

        handlerMap = new HashMap<>(4);
        handlerMap.put(TRAJ_DISTANCE_KEY, trajByDistanceHandler);
        handlerMap.put(TRAJ_DURATION_KEY, trajByDurationHandler);
        handlerMap.put(USER_PERIOD_KEY, userByPeriodHandler);
        handlerMap.put(USER_TRAJ_KEY, userByTrajHandler);

        trajByDistancePanel = new ReportItemPanel("Distribution of trajectories by distance",
                trajByDistanceHandler);
        trajByDurationPanel = new ReportItemPanel("Distribution of trajectories by effective duration",
                trajByDurationHandler);
        userByPeriodPanel = new ReportItemPanel("Distribution of users by data collection period",
                userByPeriodHandler);
        userByTrajPanel = new ReportItemPanel("Distribution of users by trajectories",
                userByTrajHandler);

        panelMap = new HashMap<>(4);
        panelMap.put(TRAJ_DISTANCE_KEY, trajByDistancePanel);
        panelMap.put(TRAJ_DURATION_KEY, trajByDurationPanel);
        panelMap.put(USER_PERIOD_KEY, userByPeriodPanel);
        panelMap.put(USER_TRAJ_KEY, userByTrajPanel);

        clientDashboardFrame = new ClientDashboardFrame(panelMap);

        kafkaMessageConsumer = new KafkaMessageConsumer(CONSUMER_TOPIC, handlerMap);
    }

    public static void main(String[] args) {
        new Dashboard().start();
    }

    void start() {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            clientDashboardFrame.setVisible(true);
        });
        //start kafka consumer
        kafkaMessageConsumer.start();
    }

//    private Map<String, String> constructSchema(String msg) {
//        String[] mParts, parts = msg.split(";");
//        Map<String, String> schema = new LinkedHashMap<>(parts.length);
//
//        for (String part : parts) {
//            mParts = part.split(":");
//            schema.put(mParts[0], mParts[1]);
//        }
//        return schema;
//    }

}
