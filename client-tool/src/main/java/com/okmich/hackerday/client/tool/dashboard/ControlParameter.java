/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.hackerday.client.tool.dashboard;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author datadev
 */
public final class ControlParameter {

    private static final Map<String, String> _map = new HashMap<>();

    public static final String PHOENIX_HOST = "phost";
    public static final String KAFKA_BROKER_URL = "kUrl";
    public static final String KAFKA_TOPIC = "kTopic";

    private ControlParameter() {
    }

    public static String get(String key) {
        return _map.get(key);
    }

    public static void set(String key, String val) {
        _map.put(key, val);
    }
}
