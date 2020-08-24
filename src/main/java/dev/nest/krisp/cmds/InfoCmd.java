package dev.nest.krisp.cmds;

import dev.nest.krisp.Krisp;
import dev.nest.krisp.objects.DataStorage;
import dev.nest.krisp.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.regex.PatternSyntaxException;

public class InfoCmd extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        DataStorage data = Krisp.getDataManager().getData(event.getGuild().getId());
        if (Utils.isPrefix(event.getGuild().getId(), message)) {
            try {
                String[] args = message.split(" ");
                if (args[0].equalsIgnoreCase(data.getPrefix() + "info")) {
                    if (args.length == 1) {
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("Bot Information");
                        builder.setDescription("Krisp Verification");
                        builder.addField("Author", "Nestirium", false);
                        builder.addField("Version", "1.0", false);
                        builder.addField("Prefix", data.getPrefix(), false);
                        builder.addField("Help Menu", "help", false);
                        builder.setFooter("This bot controls the verification system for the server!");
                        builder.setColor(Color.CYAN);
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
