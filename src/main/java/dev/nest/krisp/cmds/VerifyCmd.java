package dev.nest.krisp.cmds;

import dev.nest.krisp.Krisp;
import dev.nest.krisp.objects.DataStorage;
import dev.nest.krisp.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang.math.NumberUtils;

import java.awt.*;
import java.net.MalformedURLException;
import java.util.HashMap;

public class VerifyCmd extends ListenerAdapter {

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
            if (args[0].equalsIgnoreCase("verify")) {
                switch (args.length) {
                    case 1:
                        if (data.hasRulesImage()) {
                            builder.setThumbnail(data.getVerif_imageURL());
                        }
                        builder.setTitle("Krisp Verification Setup");
                        builder.setDescription("Commands to setup the verification system!");
                        if (data.isVerifComplete()) {
                            builder.addField("Status", "✅ I have enough information to be toggled!", false);
                        } else {
                            builder.addField("Status", "❌ I do not have enough information to be toggled!", false);
                        }
                        if (data.getVerifEnabled()) {
                            builder.addField("Enabled", "✅\nverify toggle", false);
                        } else {
                            builder.addField("Enabled", "❌\nverify toggle", false);
                        }
                        if (data.getVerifChannel() != null) {
                            builder.addField("Channel", data.getVerifChannel().getAsMention() + "\nverify setchannel <channelId>", false);
                        } else {
                            builder.addField("Channel", "❌\nverify setchannel <channelId>", false);
                        }
                        if (data.getRules_message() != null) {
                            if (!data.getRules_message().equalsIgnoreCase("null")) {
                                builder.addField("Message",  "✅\nverify setRules_message", false);
                            } else {
                                builder.addField("Message", "❌\nverify setRules_message", false);
                            }
                        } else {
                            builder.addField("Message", "❌\nverify setRules_message", false);
                        }
                        if (data.getVerif_reactionUnicode() != null) {
                            if (!data.getVerif_reactionUnicode().equalsIgnoreCase("null")) {
                                builder.addField("Reaction", data.getVerif_reactionUnicode() + "\nverify setreaction <reactionId>", false);
                            } else {
                                builder.addField("Reaction", "❌\nverify setreaction <reactionId>", false);
                            }
                        } else {
                            builder.addField("Reaction", "❌\nverify setreaction <reactionId>", false);
                        }
                        if (data.getVerif_imageURL() != null) {
                            if (!data.getVerif_imageURL().equalsIgnoreCase("null")) {
                                builder.addField("Image", data.getVerif_imageURL(), false);
                            } else {
                                builder.addField("Image", "❌\nverify setimage <url>", false);
                            }
                        } else {
                            builder.addField("Image", "❌\nverify setimage <url>", false);
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
                        if (data.getVerifRole() != null) {
                            builder.addField("Role ", data.getVerifRole().getAsMention() + "\nverify setrole <roleid>", false);
                        } else {
                            builder.addField("Role", "❌\nverify setrole <roleid>", false);
                        }
                        builder.addField("Reset ", "verify reset", false);
                        builder.setColor(Color.GREEN);
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage(builder.build()).queue();
                        builder.clear();
                        event.getMessage().delete().queue();
                        break;
                    case 2:
                        if (args[1].equalsIgnoreCase("toggle")) {
                            if (data.getVerifEnabled()) {
                                //TODO - EXCEPTION HANDLE
                                data.getVerifChannel().retrieveMessageById(data.getRules_messageId()).queue(m -> m.delete().queue());
                                data.setRules_messageId(null);
                                data.setVerifEnabled(false);
                                builder.setTitle("✅ Success");
                                builder.setDescription("Krisp Verification System is now disabled!");
                                builder.setColor(Color.RED);
                                event.getChannel().sendTyping().queue();
                                event.getChannel().sendMessage(builder.build()).queue();
                                event.getMessage().delete().queue();
                                builder.clear();
                            } else {
                                if (data.isVerifComplete()) {
                                    if (data.getVerifChannel() != null) {
                                        builder.setTitle("Krisp Verification System");
                                        if (data.getVerif_imageURL() != null) {
                                            if (!data.getVerif_imageURL().equalsIgnoreCase("null")) {
                                                builder.setImage(data.getVerif_imageURL());
                                            }
                                        }
                                        builder.setColor(Color.magenta);
                                        builder.setDescription(data.getRules_message());
                                        builder.setFooter("Bot developed by Nestirium");
                                        data.getVerifChannel().sendTyping().queue();
                                        data.getVerifChannel().sendMessage(builder.build()).queue((m) -> {
                                            m.addReaction(data.getVerif_reactionUnicode()).queue();
                                            data.setRules_messageId(m.getId());
                                        });
                                        builder.clear();
                                        data.setVerifEnabled(true);
                                        builder.setTitle("✅ Success");
                                        builder.setDescription("Krisp Verification System is now enabled!");
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
                            data.setVerifEnabled(false);
                            data.setRules_message(null);
                            try {
                                data.setVerif_imageURL(null);
                            } catch (MalformedURLException ignored) {}
                            data.setVerif_roleId(null);
                            data.setVerif_reactionUnicode(null);
                            if (data.hasVerifMessage()) {
                                if (data.getVerifChannel() != null) {
                                    //TODO - EXCEPTION HANDLE
                                    data.getVerifChannel().retrieveMessageById(data.getRules_messageId()).queue(m -> m.delete().queue());
                                }
                            }
                            data.setRules_messageId(null);
                            data.setVerif_channelId(null);
                            builder.setTitle("✅ Success");
                            builder.setDescription("Krisp Verification System is successfully reset!");
                            builder.setColor(Color.GREEN);
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage(builder.build()).queue();
                            event.getMessage().delete().queue();
                            builder.clear();
                        }
                        if (args[1].equalsIgnoreCase("setimage")) {
                            builder.setTitle("❌ Error");
                            builder.setDescription("Incorrect usage!\nverify setimage <url>");
                            builder.setColor(Color.RED);
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage(builder.build()).queue();
                            builder.clear();
                            event.getMessage().delete().queue();
                        }
                        if (args[1].equalsIgnoreCase("setchannel")) {
                            builder.setTitle("❌ Error");
                            builder.setDescription("Incorrect usage!\nverify setchannel <channelId>");
                            builder.setColor(Color.RED);
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage(builder.build()).queue();
                            builder.clear();
                            event.getMessage().delete().queue();
                        }
                        if (args[1].equalsIgnoreCase("setRules_message")) {
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
                            builder.setDescription("Incorrect usage!\nverify setreaction <reactionId>");
                            builder.setColor(Color.RED);
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage(builder.build()).queue();
                            builder.clear();
                            event.getMessage().delete().queue();
                        }
                        if (args[1].equalsIgnoreCase("setrole")) {
                            builder.setTitle("❌ Error");
                            builder.setDescription("Incorrect usage!\nverify setrole <roleid>");
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
                                data.setVerif_imageURL(args[2]);
                                builder.setTitle("✅ Success");
                                builder.setDescription("Image is now set!");
                                builder.setImage(data.getVerif_imageURL());
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
                                    data.setVerif_channelId(channel.getId());
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
                            data.setVerif_reactionUnicode(Utils.checkReactionLength(args[2]));
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
                                    data.setVerif_roleId(role.getId());
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
