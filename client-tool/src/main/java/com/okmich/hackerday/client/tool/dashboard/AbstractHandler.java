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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.okmich.hackerday.client.tool.dashboard.ControlParameter.*;

/**
 *
 * @author datadev
 */
public abstract class AbstractHandler implements Handler {

    protected ReportItem reportItem;
    protected final List<String> schema;

    protected Map<String, Double> data;

    //HBase phoenix connection
    private Connection connection = null;
    private PreparedStatement ps = null;

    /**
     *
     * @param schema
     * @param prepareStm
     */
    protected AbstractHandler(List<String> schema, String prepareStm) {
        this.schema = schema;
        try {
            //connection to phoenix server
            connection = DriverManager.getConnection("jdbc:phoenix:" + get(PHOENIX_HOST));
            ps = connection.prepareStatement(prepareStm);
        } catch (SQLException ex) {
            Logger.getLogger(AbstractHandler.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
        data = new HashMap<>(4);
    }

    @Override
    public void registerUI(ReportItem reportItem) {
        this.reportItem = reportItem;
    }

    @Override
    public List<String> getSchema() {
        return this.schema;
    }

    protected ResultSet fectResultset() throws SQLException {
        return ps.executeQuery();
    }

    protected void increasePointValue(String key) {
        data.put(key, data.get(key) + 1);
    }
}
