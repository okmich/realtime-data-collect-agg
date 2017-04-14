/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.hackerday.client.tool.dashboard;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author m.enudi
 */
public class TrajByDurationHandler implements Handler {

    private ReportItemPanel panel;
    private final Map<String, Double> model;

    public TrajByDurationHandler(List<String> keys) {
        Random rand = new Random();
        model = new LinkedHashMap<>();
        for (String key : keys) {
            model.put(key, Double.valueOf(rand.nextInt(40)));
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
