package dev.nest.krisp.listeners;

import dev.nest.krisp.Krisp;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        Krisp.getVerifDataManager().removeData(event.getGuild().getId());
        Krisp.getRuleDataManager().removeData(event.getGuild().getId());
        Krisp.getGenericDataManager().removeData(event.getGuild().getId());
        Krisp.getFileManager().deleteGuildData(event.getGuild().getId());
    }

}
