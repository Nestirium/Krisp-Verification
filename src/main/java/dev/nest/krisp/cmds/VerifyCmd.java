package dev.nest.krisp.cmds;

import dev.nest.krisp.Krisp;
import dev.nest.krisp.objects.VerificationData;
import dev.nest.krisp.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang.math.NumberUtils;

import java.awt.*;
import java.util.HashMap;

public class VerifyCmd extends ListenerAdapter {

    private final HashMap<String, Long> map = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String message = event.getMessage().getContentRaw();
        EmbedBuilder builder = new EmbedBuilder();
        VerificationData data = Krisp.getVerifDataManager().getData(event.getGuild().getId());

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
                data.setMessage(message);
                builder.setTitle("✅ Success");
                builder.setDescription("Message is now set to:\n" + data.getMessage());
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
                        if (data.hasImage()) {
                            builder.setThumbnail(data.getImageURL());
                        }
                        builder.setTitle("Krisp Verification Setup");
                        builder.setDescription("Commands to setup the verification system!");
                        if (data.isComplete()) {
                            builder.addField("Status", "✅ I have enough information to be toggled!", false);
                        } else {
                            builder.addField("Status", "❌ I do not have enough information to be toggled!", false);
                        }
                        if (data.isEnabled()) {
                            builder.addField("Enabled", "✅\nverify toggle", false);
                        } else {
                            builder.addField("Enabled", "❌\nverify toggle", false);
                        }
                        if (data.getChannel() != null) {
                            builder.addField("Channel", data.getChannel().getAsMention() + "\nverify setchannel <channelId>", false);
                        } else {
                            builder.addField("Channel", "❌\nverify setchannel <channelId>", false);
                        }
                        if (data.getMessage() != null) {
                            if (!data.getMessage().equalsIgnoreCase("null")) {
                                builder.addField("Message",  "✅\nverify setmessage", false);
                            } else {
                                builder.addField("Message", "❌\nverify setmessage", false);
                            }
                        } else {
                            builder.addField("Message", "❌\nverify setmessage", false);
                        }
                        if (data.getReactionUnicode() != null) {
                            if (!data.getReactionUnicode().equalsIgnoreCase("null")) {
                                builder.addField("Reaction", data.getReactionUnicode() + "\nverify setreaction <reactionId>", false);
                            } else {
                                builder.addField("Reaction", "❌\nverify setreaction <reactionId>", false);
                            }
                        } else {
                            builder.addField("Reaction", "❌\nverify setreaction <reactionId>", false);
                        }
                        if (data.getImageURL() != null) {
                            if (!data.getImageURL().equalsIgnoreCase("null")) {
                                builder.addField("Image", data.getImageURL(), false);
                            } else {
                                builder.addField("Image", "❌\nverify setimage <url>", false);
                            }
                        } else {
                            builder.addField("Image", "❌\nverify setimage <url>", false);
                        }
                        if (data.getMessageId() != null) {
                            if (!data.getMessageId().equalsIgnoreCase("null")) {
                                builder.addField("Message ID", data.getMessageId(), false);
                            } else {
                                builder.addField("Message ID", "❌", false);
                            }
                        } else {
                            builder.addField("Message ID", "❌", false);
                        }
                        if (data.getRole() != null) {
                            builder.addField("Role ", data.getRole().getAsMention() + "\nverify setrole <roleid>", false);
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
                            if (data.isEnabled()) {
                                //TODO - EXCEPTION HANDLE
                                data.getChannel().retrieveMessageById(data.getMessageId()).queue(m -> m.delete().queue());
                                data.setMessageId(null);
                                data.setEnabled(false);
                                builder.setTitle("✅ Success");
                                builder.setDescription("Krisp Verification System is now disabled!");
                                builder.setColor(Color.RED);
                                event.getChannel().sendTyping().queue();
                                event.getChannel().sendMessage(builder.build()).queue();
                                event.getMessage().delete().queue();
                                builder.clear();
                            } else {
                                if (data.isComplete()) {
                                    if (data.getChannel() != null) {
                                        builder.setTitle("Krisp Verification System");
                                        if (data.getImageURL() != null) {
                                            if (!data.getImageURL().equalsIgnoreCase("null")) {
                                                builder.setImage(data.getImageURL());
                                            }
                                        }
                                        builder.setColor(Color.magenta);
                                        builder.setDescription(data.getMessage());
                                        builder.setFooter("Bot developed by Nestirium");
                                        data.getChannel().sendTyping().queue();
                                        data.getChannel().sendMessage(builder.build()).queue((m) -> {
                                            m.addReaction(data.getReactionUnicode()).queue();
                                            data.setMessageId(m.getId());
                                        });
                                        builder.clear();
                                        data.setEnabled(true);
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
                            data.setEnabled(false);
                            data.setMessage(null);
                            data.setImageURL(null);
                            data.setRoleId(null);
                            data.setReactionUnicode(null);
                            if (data.hasMessage()) {
                                if (data.getChannel() != null) {
                                    //TODO - EXCEPTION HANDLE
                                    data.getChannel().retrieveMessageById(data.getMessageId()).queue(m -> m.delete().queue());
                                }
                            }
                            data.setMessageId(null);
                            data.setChannelId(null);
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
                            if (Utils.isURL(args[2])) {
                                data.setImageURL(args[2]);
                                builder.setTitle("✅ Success");
                                builder.setDescription("Image is now set!");
                                builder.setImage(data.getImageURL());
                                builder.setColor(Color.GREEN);
                                event.getChannel().sendTyping().queue();
                                event.getChannel().sendMessage(builder.build()).queue();
                                builder.clear();
                                event.getMessage().delete().queue();
                            } else {
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
                                    data.setChannelId(channel.getId());
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
                            data.setReactionUnicode(Utils.checkReactionLength(args[2]));
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
                                    data.setRoleId(role.getId());
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
