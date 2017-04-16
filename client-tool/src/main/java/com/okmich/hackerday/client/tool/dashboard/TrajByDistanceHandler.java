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
public class TrajByDistanceHandler extends AbstractHandler {

    private static final String ITEM1 = "< 5km";
    private static final String ITEM2 = "5km ~ 20km";
    private static final String ITEM3 = "20km ~ 100km";
    private static final String ITEM4 = "≥ 100km";

    private static final String SQL_QUERY = "select \"trajid\", sum(\"dist\") distance from \"mvment_by_traj\" group by \"trajid\"";

    /**
     *
     */
    public TrajByDistanceHandler() {
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
            Logger.getLogger(TrajByDistanceHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void increasePoints(long l) {
        long km = l / 1000;
        if (km <= 5) {
            increasePointValue(ITEM1);
        } else if (km > 5 && km <= 20) {
            increasePointValue(ITEM2);
        } else if (km > 20 && km <= 100) {
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
