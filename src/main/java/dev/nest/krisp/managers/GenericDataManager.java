package dev.nest.krisp.managers;

import dev.nest.krisp.objects.GenericData;

import java.util.concurrent.ConcurrentHashMap;

public class GenericDataManager {

    private ConcurrentHashMap<String, GenericData> dataMap;

    public GenericDataManager() {
        dataMap = new ConcurrentHashMap<>();
    }

    public void putData(String guildId, GenericData data) {
        dataMap.put(guildId, data);
    }

    public void removeData(String guildId) {
        dataMap.remove(guildId);
    }

    public GenericData getData(String guildId) {
        return dataMap.get(guildId);
    }

    public ConcurrentHashMap<String, GenericData> getDataMap() {
        return dataMap;
    }

}
