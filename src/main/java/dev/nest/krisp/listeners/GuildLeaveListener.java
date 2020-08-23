package dev.nest.krisp.listeners;

import dev.nest.krisp.Krisp;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        Krisp.getDataManager().removeData(event.getGuild().getId());
        Krisp.getHandler().deleteData(event.getGuild().getId());
    }

}
