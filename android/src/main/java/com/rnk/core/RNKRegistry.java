package com.rnk.core;

import java.util.HashMap;
import java.util.Map;

@FunctionalInterface
interface EventHandler {
    boolean execute(Object data);
}
public class RNKRegistry {
    public static RNKRegistry instance = new RNKRegistry();
    public static RNKRegistry getInstance() { return instance; }
    Map<String, Object> data = new HashMap<String, Object>();
    Map<String, Map<String, EventHandler>> events = new HashMap<String, Map<String, EventHandler>>();
    public void registerPackage(RNKPackageInterface p) {
        p.createEventManagers(this);
    }
    public Object get(String key) {
        return data.get(key);
    }
    public void set(String key, Object value) {
        data.put(key, value);
    }
    protected Map<String, EventHandler> getKeys(String event) {
        Map<String, EventHandler> thisEvents = events.get(event);
        if(thisEvents == null) thisEvents = new HashMap<String, EventHandler>();
        return thisEvents;
    }
    public void add(String event, String key, EventHandler func) {
        Map<String, EventHandler> thisEvents = getKeys(event);
        thisEvents.put(key, func);
    }
    public void remove(String event, String key) {
        Map<String, EventHandler> thisEvents = getKeys(event);
        thisEvents.remove(key);
    }
    public Boolean trigger(String event, Object data) {
        Map<String, EventHandler> thisEvents = getKeys(event);
        thisEvents = getKeys(event);
        for(Map.Entry<String, EventHandler> entry: thisEvents.entrySet()){
            boolean temp = entry.getValue().execute(data);
            if(temp == false) return false;
        }
        return true;
    }
    public Boolean trigger(String event) {
        return trigger(event, null);
    }

}