package com.rnk.core
class RNKRegistry {
    static RNKRegistry instance = RNKRegistry();
    Map<String, Object> data = new Map();
    Map<String, <String, ()->Boolean>> events = new Map();
    public void registerPackage(RNKPackage package) {
        package.createEventManagers(this)
    }
    public Object getData(String key) = {
        return data.get(key)
    }
    public void setData(String key, Object value) {
        data.put(key, value)
    }
    protected Map<String, ()->Boolean> getKeys(String event) {
        Map<String, ()->Void> thisEvents = events.get(event)
        if(thisEvents == null) thisEvents = new Map()
        return thisEvents;
    }
    public void addEvent(String event, String key, (data)->Boolean func) {
        thisEvents = getKeys(event)
        thisEvents.put(key, func);
    }
    public void removeEvent(String event, String key) {
       thisEvents = getKeys(event)
        thisEvents.remove(key)
    }
    public Boolean triggerEvent(String event, Object data) { Map<String, ()->Void> thisEvents = events.get(event)
        thisEvents = getKeys(event)
        for(Map.entry<String, ()->Void> entry: thisEvents.entrySet){
            entry.value(data)
        }
    }

}