/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.hackerday.client.tool.dashboard;

import java.util.Map;

/**
 *
 * @author datadev
 */
public abstract class AbstractHandler implements Handler {

    protected ReportItem reportItem;
    protected final Map<String, String> model;

    protected AbstractHandler(Map<String, String> schema) {
        model = schema;
    }

    @Override
    public void registerUI(ReportItem reportItem) {
        this.reportItem = reportItem;
    }

    @Override
    public Map<String, String> getModel() {
        return this.model;
    }

}
