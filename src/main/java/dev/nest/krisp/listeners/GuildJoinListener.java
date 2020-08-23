package dev.nest.krisp.listeners;

import dev.nest.krisp.Krisp;
import dev.nest.krisp.objects.DataStorage;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildJoinListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        new DataStorage("~", event.getGuild().getId());
        Krisp.getHandler().writeData(event.getGuild().getId());
    }

}
