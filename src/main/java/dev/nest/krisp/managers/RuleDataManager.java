package dev.nest.krisp.managers;

import dev.nest.krisp.objects.RulesData;

import java.util.concurrent.ConcurrentHashMap;

public class RuleDataManager {

    private ConcurrentHashMap<String, RulesData> dataMap;

    public RuleDataManager() {
        dataMap = new ConcurrentHashMap<>();
    }

    public void putData(String guildId, RulesData data) {
        dataMap.put(guildId, data);
    }

    public void removeData(String guildId) {
        dataMap.remove(guildId);
    }

    public RulesData getData(String guildId) {
        return dataMap.get(guildId);
    }

    public ConcurrentHashMap<String, RulesData> getDataMap() {
        return dataMap;
    }

}
