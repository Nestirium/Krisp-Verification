package dev.nest.krisp.objects;

import dev.nest.krisp.Krisp;

public class GenericData {

    private String prefix;
    private final String guildId;

    public GenericData(String prefix, String guildId) {
        this.prefix = prefix;
        this.guildId = guildId;
        Krisp.getGenericDataManager().putData(this.guildId, this);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public String toString() {
        return prefix;
    }

}
