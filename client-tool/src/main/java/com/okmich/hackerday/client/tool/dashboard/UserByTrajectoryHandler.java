/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.hackerday.client.tool.dashboard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m.enudi
 */
public class UserByTrajectoryHandler extends AbstractHandler {

    private static final String ITEM1 = "< 10";
    private static final String ITEM2 = "10 ~ 50";
    private static final String ITEM3 = "50 ~ 100";
    private static final String ITEM4 = "≥ 100";

    private static final String SQL_QUERY = "select \"userId\", count(distinct \"trajId\") from \"mvment\" group by \"userId\"";

    /**
     *
     */
    public UserByTrajectoryHandler() {
        super(Arrays.asList(new String[]{ITEM1, ITEM2, ITEM3, ITEM4}), SQL_QUERY);
        initData();
    }

    @Override
    public void handle() {
        try {
            ResultSet rs = fectResultset();
            initData();
            while (rs.next()) {
                increasePoints(rs.getLong(2));
            }
            this.reportItem.reportItemModel(data);
        } catch (SQLException ex) {
            Logger.getLogger(UserByTrajectoryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void increasePoints(long l) {
        if (l <= 10) {
            increasePointValue(ITEM1);
        } else if (l > 10 && l <= 50) {
            increasePointValue(ITEM2);
        } else if (l > 50 && l <= 100) {
            increasePointValue(ITEM3);
        } else {
            increasePointValue(ITEM4);
        }
    }

    private void initData() {
        this.data.clear();

        data.put(ITEM1, 0.0);
        data.put(ITEM2, 0.0);
        data.put(ITEM3, 0.0);
        data.put(ITEM4, 0.0);
    }

}
