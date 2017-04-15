/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.hackerday.client.tool.dashboard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author datadev
 */
public abstract class AbstractHandler implements Handler {

    protected ReportItem reportItem;
    protected final Map<String, String> model;

    //HBase phoenix connection
    private Connection connection = null;
    private PreparedStatement ps = null;

    /**
     *
     * @param schema
     * @param prepareStm
     */
    protected AbstractHandler(Map<String, String> schema, String prepareStm) {
        model = schema;
        try {
            //connection to phoenix server
            connection = DriverManager.getConnection("jdbc:phoenix:datadev-box");
            ps = connection.prepareStatement(prepareStm);
        } catch (SQLException ex) {
            Logger.getLogger(AbstractHandler.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    protected ResultSet fectResultset() throws SQLException {
        return ps.executeQuery();
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
