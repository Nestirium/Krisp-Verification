package dev.nest.krisp.cmds;

import dev.nest.krisp.Krisp;
import dev.nest.krisp.objects.DataStorage;
import dev.nest.krisp.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.regex.PatternSyntaxException;

public class HelpCmd extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        DataStorage data = Krisp.getDataManager().getData(event.getGuild().getId());
        if (Utils.isPrefix(event.getGuild().getId(), message)) {
           try {
               EmbedBuilder builder = new EmbedBuilder();
               String[] args = message.split(" ");
               if (args[0].equalsIgnoreCase(data.getPrefix() + "help")) {
                   if (args.length == 1) {
                       builder.setTitle("Commands");
                       builder.setDescription("List of all the available commands.");
                       builder.addField("Current Prefix", data.getPrefix(), false);
                       builder.addField("prefix", "setprefix <prefix>", false);
                       builder.addField("help", "Shows this message.", false);
                       builder.addField("info", "Shows the bot information.", false);
                       builder.addField("verify", "Starts the verification system setup wizard.", false);
                       builder.addField("rules", "Starts the rules system setup wizard.", false);
                       builder.setColor(Color.BLUE);
                       event.getChannel().sendTyping().queue();
                       event.getChannel().sendMessage(builder.build()).queue();
                       builder.clear();
                       event.getMessage().delete().queue();
                   }
               }
           } catch (PatternSyntaxException ignored) {}

        }
    }

}
