package dev.nest.krisp.listeners;

import dev.nest.krisp.Krisp;
import dev.nest.krisp.enums.DataType;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildJoinListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        Krisp.getFileManager().setupData(event.getGuild().getId());
        Krisp.getFileManager().writeData(event.getGuild().getId(), DataType.GENERIC, true);
        Krisp.getFileManager().writeData(event.getGuild().getId(), DataType.VERIF, true);
        Krisp.getFileManager().writeData(event.getGuild().getId(), DataType.RULES, true);
    }

}
