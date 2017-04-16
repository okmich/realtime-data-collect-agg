/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.hackerday.client.tool.dashboard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m.enudi
 */
public class UserByPeriodHandler extends AbstractHandler {

    private static final String ITEM1 = "< 1week";
    private static final String ITEM2 = "1week ~ 1month";
    private static final String ITEM3 = "1month ~ 1year";
    private static final String ITEM4 = "≥1year";

    private static final String SQL_QUERY = "select \"userId\", min(\"ts\") mints, max(\"ts\") maxts from \"mvment\" group by \"userId\"";

    /**
     *
     */
    public UserByPeriodHandler() {
        super(Arrays.asList(new String[]{ITEM1, ITEM2, ITEM3, ITEM4}), SQL_QUERY);
        initData();
    }

    @Override
    public void handle(String msg) {
        try {
            ResultSet rs = fectResultset();
            initData();
            while (rs.next()) {
                increasePoints(rs.getLong(2), rs.getLong(3));
            }
            this.reportItem.reportItemModel(data);
        } catch (SQLException ex) {
            Logger.getLogger(TrajByDistanceHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void increasePoints(long mints, long maxts) {
        Instant date1 = Instant.ofEpochMilli(mints);
        Instant date2 = Instant.ofEpochMilli(maxts);

        Duration duration = Duration.between(date1, date2).abs();
        long days = duration.toDays();
        if (days <= 7) {
            increasePointValue(ITEM1);
        } else if (days > 7 && days <= 30) {
            increasePointValue(ITEM2);
        } else if (days > 30 && days <= 365) {
            increasePointValue(ITEM3);
        } else {
            increasePointValue(ITEM4);
        }
    }

    private void initData() {
        data.clear();

        data.put(ITEM1, 0.0);
        data.put(ITEM2, 0.0);
        data.put(ITEM3, 0.0);
        data.put(ITEM4, 0.0);
    }

}
