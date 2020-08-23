package dev.nest.krisp.objects;


import java.util.concurrent.ConcurrentHashMap;

public class DataManager {

    private final ConcurrentHashMap<String, DataStorage> dataMap;

    public DataManager() {
        dataMap = new ConcurrentHashMap<>();
    }

    public void putData(String guildId, DataStorage data) {
        dataMap.put(guildId, data);
    }

    public void removeData(String guildId) {
        dataMap.remove(guildId);
    }

    public DataStorage getData(String guildId) {
        return dataMap.get(guildId);
    }

    public ConcurrentHashMap<String, DataStorage> getDataMap() {
        return dataMap;
    }

}
