/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.hackerday.client.tool.dashboard;

import java.util.Map;

/**
 *
 * @author m.enudi
 */
public class TrajByDurationHandler extends AbstractHandler {

    private static final String SQL_QUERY = "select \"trajid\", sum(\"tdiffs\") duration from \"mvment_by_traj\" group by \"trajId\"";

    /**
     *
     * @param schema
     */
    public TrajByDurationHandler(Map<String, String> schema) {
        super(schema, SQL_QUERY);
    }

    @Override
    public void handle(String msg) {

    }
}
