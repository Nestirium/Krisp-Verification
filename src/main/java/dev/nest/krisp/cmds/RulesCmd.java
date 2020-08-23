package dev.nest.krisp.cmds;

import dev.nest.krisp.Krisp;
import dev.nest.krisp.objects.DataStorage;
import dev.nest.krisp.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang.math.NumberUtils;

import java.awt.*;
import java.net.MalformedURLException;
import java.util.HashMap;

public class RulesCmd extends ListenerAdapter {

    private final HashMap<String, Long> map = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String message = event.getMessage().getContentRaw();
        EmbedBuilder builder = new EmbedBuilder();
        DataStorage data = Krisp.getDataManager().getData(event.getGuild().getId());

        if (map.containsKey(event.getAuthor().getId())) {
            if ((System.currentTimeMillis() - map.get(event.getAuthor().getId())) > 180000) {
                map.remove(event.getAuthor().getId());
                builder.setTitle("❌ Error");
                builder.setDescription("You ran out of time writing the message!");
                builder.setColor(Color.RED);
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
                event.getMessage().delete().queue();
            } else {
                try {
                    data.setRules_message(message);
                } catch (IllegalArgumentException e) {
                    builder.setTitle("❌ Error");
                    builder.setDescription("Message cannot be longer than 1024 characters! You are now at " + message.length() + " characters!");
                    builder.setColor(Color.RED);
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(builder.build()).queue();
                    builder.clear();
                    event.getMessage().delete().queue();
                }
                builder.setTitle("✅ Success");
                builder.setDescription("Message is now set to:\n" + data.getRules_message());
                builder.setColor(Color.GREEN);
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
                event.getMessage().delete().queue();
                map.remove(event.getAuthor().getId());
            }
        }

        if (Utils.isPrefix(event.getGuild().getId(), message)) {
            String s = Utils.stripPrefix(event.getGuild().getId(), message);
            String[] args = s.split(" ");
            if (args[0].equalsIgnoreCase("rules")) {
                switch (args.length) {
                    case 1:
                        if (data.hasRulesImage()) {
                            builder.setThumbnail(data.getRules_imageURL());
                        }
                        builder.setTitle("Krisp Rules Setup");
                        builder.setDescription("Commands to setup the rules system!");
                        if (data.isRulesComplete()) {
                            builder.addField("Status", "✅ I have enough information to be toggled!", false);
                        } else {
                            builder.addField("Status", "❌ I do not have enough information to be toggled!", false);
                        }
                        if (data.getRulesEnabled()) {
                            builder.addField("Enabled", "✅\nrules toggle", false);
                        } else {
                            builder.addField("Enabled", "❌\nrules toggle", false);
                        }
                        if (data.getRulesChannel() != null) {
                            builder.addField("Channel", data.getRulesChannel().getAsMention() + "\nrules setchannel <channelId>", false);
                        } else {
                            builder.addField("Channel", "❌\nrules setchannel <channelId>", false);
                        }
                        if (data.getRules_message() != null) {
                            if (!data.getRules_message().equalsIgnoreCase("null")) {
                                builder.addField("Message", "✅\nrules setmessage", false);
                            } else {
                                builder.addField("Message", "❌\nrules setmessage", false);
                            }
                        } else {
                            builder.addField("Message", "❌\nrules setmessage", false);
                        }
                        if (data.getRules_reactionUnicode() != null) {
                            if (!data.getRules_reactionUnicode().equalsIgnoreCase("null")) {
                                builder.addField("Reaction", data.getRules_reactionUnicode() + "\nrules setreaction <reactionId>", false);
                            } else {
                                builder.addField("Reaction", "❌\nrules setreaction <reactionId>", false);
                            }
                        } else {
                            builder.addField("Reaction", "❌\nrules setreaction <reactionId>", false);
                        }
                        if (data.getRules_imageURL() != null) {
                            if (!data.getRules_imageURL().equalsIgnoreCase("null")) {
                                builder.addField("Image", data.getRules_imageURL(), false);
                            } else {
                                builder.addField("Image", "❌\nrules setimage <url>", false);
                            }
                        } else {
                            builder.addField("Image", "❌\nrules setimage <url>", false);
                        }
                        if (data.getRules_messageId() != null) {
                            if (!data.getRules_messageId().equalsIgnoreCase("null")) {
                                builder.addField("Message ID", data.getRules_messageId(), false);
                            } else {
                                builder.addField("Message ID", "❌", false);
                            }
                        } else {
                            builder.addField("Message ID", "❌", false);
                        }
                        if (data.getRulesRole() != null) {
                            builder.addField("Role ", data.getRulesRole().getAsMention() + "\nrules setrole <roleid>", false);
                        } else {
                            builder.addField("Role", "❌\nrules setrole <roleid>", false);
                        }
                        builder.addField("Reset ", "rules reset", false);
                        builder.setColor(Color.GREEN);
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage(builder.build()).queue();
                        builder.clear();
                        event.getMessage().delete().queue();
                        break;
                    case 2:
                        if (args[1].equalsIgnoreCase("toggle")) {
                            if (data.getRulesEnabled()) {
                                //TODO - EXCEPTION HANDLE
                                data.getRulesChannel().retrieveMessageById(data.getRules_messageId()).queue(m -> m.delete().queue());
                                data.setRules_messageId(null);
                                data.setRulesEnabled(false);
                                builder.setTitle("✅ Success");
                                builder.setDescription("Krisp Rules System is now disabled!");
                                builder.setColor(Color.RED);
                                event.getChannel().sendTyping().queue();
                                event.getChannel().sendMessage(builder.build()).queue();
                                event.getMessage().delete().queue();
                                builder.clear();
                            } else {
                                if (data.isRulesComplete()) {
                                    if (data.getRulesChannel() != null) {
                                        builder.setTitle("Krisp Rules System");
                                        if (data.getRules_imageURL() != null) {
                                            if (!data.getRules_imageURL().equalsIgnoreCase("null")) {
                                                builder.setImage(data.getRules_imageURL());
                                            }
                                        }
                                        builder.setColor(Color.magenta);
                                        builder.setDescription(data.getRules_message());
                                        builder.setFooter("Bot developed by Nestirium");
                                        data.getRulesChannel().sendTyping().queue();
                                        data.getRulesChannel().sendMessage(builder.build()).queue((m) -> {
                                            m.addReaction(data.getRules_reactionUnicode()).queue();
                                            data.setRules_messageId(m.getId());
                                        });
                                        builder.clear();
                                        data.setRulesEnabled(true);
                                        builder.setTitle("✅ Success");
                                        builder.setDescription("Krisp Rules System is now enabled!");
                                        builder.setColor(Color.GREEN);
                                        event.getChannel().sendTyping().queue();
                                        event.getChannel().sendMessage(builder.build()).queue();
                                        event.getMessage().delete().queue();
                                        builder.clear();
                                    }
                                } else {
                                    builder.setTitle("❌ Error");
                                    builder.setDescription("There are still information required to enable the system!");
                                    builder.setColor(Color.RED);
                                    event.getChannel().sendTyping().queue();
                                    event.getChannel().sendMessage(builder.build()).queue();
                                    event.getMessage().delete().queue();
                                    builder.clear();
                                }
                            }
                        }
                        if (args[1].equalsIgnoreCase("reset")) {
                            data.setRulesEnabled(false);
                            data.setRules_message(null);
                            try {
                                data.setRules_imageURL(null);
                            } catch (MalformedURLException ignored) {}
                            data.setRules_roleId(null);
                            data.setRules_reactionUnicode(null);
                            if (data.hasRulesMessage()) {
                                if (data.getRulesChannel() != null) {
                                    //TODO - EXCEPTION HANDLE
                                    data.getRulesChannel().retrieveMessageById(data.getRules_messageId()).queue(m -> m.delete().queue());
                                }
                            }
                            data.setRules_messageId(null);
                            data.setRules_channelId(null);
                            builder.setTitle("✅ Success");
                            builder.setDescription("Krisp Rules System is successfully reset!");
                            builder.setColor(Color.GREEN);
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage(builder.build()).queue();
                            event.getMessage().delete().queue();
                            builder.clear();
                        }
                        if (args[1].equalsIgnoreCase("setimage")) {
                            builder.setTitle("❌ Error");
                            builder.setDescription("Incorrect usage!\nrules setimage <url>");
                            builder.setColor(Color.RED);
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage(builder.build()).queue();
                            builder.clear();
                            event.getMessage().delete().queue();
                        }
                        if (args[1].equalsIgnoreCase("setchannel")) {
                            builder.setTitle("❌ Error");
                            builder.setDescription("Incorrect usage!\nrules setchannel <channelId>");
                            builder.setColor(Color.RED);
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage(builder.build()).queue();
                            builder.clear();
                            event.getMessage().delete().queue();
                        }
                        if (args[1].equalsIgnoreCase("setmessage")) {
                            map.put(event.getAuthor().getId(), System.currentTimeMillis());
                            builder.setTitle("ℹ️ Info");
                            builder.setDescription("Please specify the message you want to set within 3 minutes!");
                            builder.setColor(Color.YELLOW);
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage(builder.build()).queue();
                            builder.clear();
                            event.getMessage().delete().queue();
                        }
                        if (args[1].equalsIgnoreCase("setreaction")) {
                            builder.setTitle("❌ Error");
                            builder.setDescription("Incorrect usage!\nrules setreaction <reactionId>");
                            builder.setColor(Color.RED);
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage(builder.build()).queue();
                            builder.clear();
                            event.getMessage().delete().queue();
                        }
                        if (args[1].equalsIgnoreCase("setrole")) {
                            builder.setTitle("❌ Error");
                            builder.setDescription("Incorrect usage!\nrules setrole <roleid>");
                            builder.setColor(Color.RED);
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage(builder.build()).queue();
                            builder.clear();
                            event.getMessage().delete().queue();
                        }
                        break;
                    case 3:
                        if (args[1].equalsIgnoreCase("setimage")) {
                                try {
                                    data.setRules_imageURL(args[2]);
                                    builder.setTitle("✅ Success");
                                    builder.setDescription("Image is now set!");
                                    builder.setImage(data.getRules_imageURL());
                                    builder.setColor(Color.GREEN);
                                    event.getChannel().sendTyping().queue();
                                    event.getChannel().sendMessage(builder.build()).queue();
                                    builder.clear();
                                    event.getMessage().delete().queue();
                                } catch (MalformedURLException e) {
                                    builder.setTitle("❌ Error");
                                    builder.setDescription("Invalid URL specified!");
                                    builder.setColor(Color.RED);
                                    event.getChannel().sendTyping().queue();
                                    event.getChannel().sendMessage(builder.build()).queue();
                                    builder.clear();
                                }
                        }
                        if (args[1].equalsIgnoreCase("setchannel")) {
                            if (NumberUtils.isNumber(args[2])) {
                                TextChannel channel = Krisp.getJDA().getTextChannelById(args[2]);
                                if (channel != null) {
                                    data.setRules_channelId(channel.getId());
                                    builder.setTitle("✅ Success");
                                    builder.setDescription("Channel is now set to " + channel.getAsMention());
                                    builder.setColor(Color.GREEN);
                                } else {
                                    builder.setTitle("❌ Error");
                                    builder.setDescription("Invalid channel specified!");
                                    builder.setColor(Color.RED);
                                }
                            } else {
                                builder.setTitle("❌ Error");
                                builder.setDescription("Channel ID must be a number!");
                                builder.setColor(Color.RED);
                            }
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage(builder.build()).queue();
                            builder.clear();
                            event.getMessage().delete().queue();
                        }
                        if (args[1].equalsIgnoreCase("setreaction")) {
                            //TODO - ADD VALID EMOJI UNICODE CHECK
                            if (NumberUtils.isNumber(args[2])) {
                                builder.setTitle("❌ Error");
                                builder.setDescription("Invalid reaction specified!");
                                builder.setColor(Color.RED);
                                event.getChannel().sendTyping().queue();
                                event.getChannel().sendMessage(builder.build()).queue();
                                builder.clear();
                                event.getMessage().delete().queue();
                                return;
                            }
                            data.setRules_reactionUnicode(Utils.checkReactionLength(args[2]));
                            builder.setTitle("✅ Success");
                            builder.setDescription("Reaction is now set to " + args[2]);
                            builder.setColor(Color.GREEN);
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage(builder.build()).queue();
                            builder.clear();
                            event.getMessage().delete().queue();
                        }
                        if (args[1].equalsIgnoreCase("setrole")) {
                            if (NumberUtils.isNumber(args[2])) {
                                Role role = Krisp.getJDA().getRoleById(args[2]);
                                if (role != null) {
                                    data.setRules_roleId(role.getId());
                                    builder.setTitle("✅ Success");
                                    builder.setDescription("Role is now set to " + role.getAsMention());
                                    builder.setColor(Color.GREEN);
                                } else {
                                    builder.setTitle("❌ Error");
                                    builder.setDescription("Invalid role specified!");
                                    builder.setColor(Color.RED);
                                }
                            } else {
                                builder.setTitle("❌ Error");
                                builder.setDescription("Role ID must be a number!");
                                builder.setColor(Color.RED);
                            }
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage(builder.build()).queue();
                            builder.clear();
                            event.getMessage().delete().queue();
                        }
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
