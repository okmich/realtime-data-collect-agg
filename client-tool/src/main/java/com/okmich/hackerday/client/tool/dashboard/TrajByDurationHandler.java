/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.hackerday.client.tool.dashboard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m.enudi
 */
public class TrajByDurationHandler extends AbstractHandler {

    private static final String ITEM1 = "< 1h";
    private static final String ITEM2 = "1h ~ 6h";
    private static final String ITEM3 = "6h ~ 12h";
    private static final String ITEM4 = "≥ 12h";

    private static final String SQL_QUERY = "select \"trajid\", sum(\"tdiffs\") duration from \"mvment_by_traj\" group by \"trajid\"";

    /**
     *
     */
    public TrajByDurationHandler() {
        super(Arrays.asList(new String[]{ITEM1, ITEM2, ITEM3, ITEM4}), SQL_QUERY);
        initData();
    }

    @Override
    public void handle(String msg) {
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
        float hrs = l / 3600f;
        if (hrs <= 1) {
            increasePointValue(ITEM1);
        } else if (hrs > 1 && hrs <= 6) {
            increasePointValue(ITEM2);
        } else if (hrs > 6 && hrs <= 12) {
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
