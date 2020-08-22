package dev.nest.krisp.managers;

import dev.nest.krisp.objects.VerificationData;

import java.util.concurrent.ConcurrentHashMap;

public class VerifDataManager {

    private ConcurrentHashMap<String, VerificationData> dataMap;

    public VerifDataManager() {
        dataMap = new ConcurrentHashMap<>();
    }

    public void putData(String guildId, VerificationData data) {
        dataMap.put(guildId, data);
    }

    public void removeData(String guildId) {
        dataMap.remove(guildId);
    }

    public VerificationData getData(String guildId) {
        return dataMap.get(guildId);
    }

    public ConcurrentHashMap<String, VerificationData> getDataMap() {
        return dataMap;
    }

}
