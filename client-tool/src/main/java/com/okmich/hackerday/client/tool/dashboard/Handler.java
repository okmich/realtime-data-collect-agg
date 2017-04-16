/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.hackerday.client.tool.dashboard;

import java.util.List;

/**
 *
 * @author m.enudi
 */
public interface Handler {

    /**
     * TRAJ_DISTANCE_KEY
     */
    public static final String TRAJ_DISTANCE_KEY = "traj.dist";
    /**
     * TRAJ_DURATION_KEY
     */
    public static final String TRAJ_DURATION_KEY = "traj.drtn";
    /**
     * USER_TRAJ_KEY
     */
    public static final String USER_TRAJ_KEY = "user.traj";
    /**
     * USER_PERIOD_KEY
     */
    public static final String USER_PERIOD_KEY = "user.prd";

    /**
     *
     */
    void handle();

    /**
     *
     * @return
     */
    List<String> getSchema();

    /**
     *
     * @param reportItem
     */
    void registerUI(ReportItem reportItem);

}
