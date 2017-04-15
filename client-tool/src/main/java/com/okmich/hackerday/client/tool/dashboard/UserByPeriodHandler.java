/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.hackerday.client.tool.dashboard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m.enudi
 */
public class UserByPeriodHandler extends AbstractHandler {

    private static final String SQL_QUERY = "select \"userId\", min(\"ts\") mints, max(\"ts\") maxts from \"mvment\" group by \"trajId\"";

    /**
     *
     * @param schema
     */
    public UserByPeriodHandler(Map<String, String> schema) {
        super(schema, SQL_QUERY);
    }

    @Override
    public void handle(String msg) {
//        try {
//            ResultSet rs = fectResultset();
//
//            Map<String, Double> data = null;
//            this.reportItem.reportItemModel(data);
//        } catch (SQLException ex) {
//            Logger.getLogger(UserByPeriodHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
