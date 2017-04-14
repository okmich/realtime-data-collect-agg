/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.hackerday.client.tool.dashboard;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author m.enudi
 */
public class UserByTrajectoryHandler implements Handler {

    private ReportItemPanel panel;
    private final Map<String, Double> model;

    public UserByTrajectoryHandler(List<String> keys) {
        model = new LinkedHashMap<>();
        for (String key : keys) {
            model.put(key, 0d);
        }
    }

    @Override
    public void handle() {

    }

    @Override
    public void registerUI(ReportItemPanel reportItemPanel) {
        this.panel = reportItemPanel;
    }

    @Override
    public Map<String, Double> getModel() {
        return this.model;
    }

}
