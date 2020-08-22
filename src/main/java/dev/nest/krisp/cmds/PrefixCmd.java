package dev.nest.krisp.cmds;

import dev.nest.krisp.Krisp;
import dev.nest.krisp.objects.GenericData;
import dev.nest.krisp.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class PrefixCmd extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        GenericData data = Krisp.getGenericDataManager().getData(event.getGuild().getId());
        if (Utils.isPrefix(event.getGuild().getId(), message)) {
            String s = Utils.stripPrefix(event.getGuild().getId(), message);
            String[] args = s.split(" ");
            if (args[0].equalsIgnoreCase("setprefix")) {
                EmbedBuilder builder = new EmbedBuilder();
                switch (args.length) {
                    case 1:
                        builder.setTitle("❌ Error");
                        builder.setDescription("Usage: setprefix <prefix>");
                        builder.setColor(Color.RED);
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage(builder.build()).queue();
                        builder.clear();
                        event.getMessage().delete().queue();
                        break;
                    case 2:
                        data.setPrefix(args[1]);
                        builder.setTitle("✅ Success");
                        builder.setDescription("Prefix is now set to " + args[1]);
                        builder.setColor(Color.GREEN);
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage(builder.build()).queue();
                        builder.clear();
                        event.getMessage().delete().queue();
                        break;
                    default:
                        builder.setTitle("❌ Error");
                        builder.setDescription("Incorrect Usage! Please check help!");
                        builder.setColor(Color.RED);
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage(builder.build()).queue();
                        builder.clear();
                        event.getMessage().delete().queue();
                        break;
                }
            }
        }
    }

}
