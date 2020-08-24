package dev.nest.krisp.listeners;

import dev.nest.krisp.Krisp;
import dev.nest.krisp.objects.DataStorage;
import dev.nest.krisp.utils.Utils;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


public class ReactionAddListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        DataStorage data = Krisp.getDataManager().getData(event.getGuild().getId());
        if (data.getVerifEnabled()) {
            if (data.getVerifChannel() != null) {
                event.retrieveMember().queue((m) -> {
                    if (!m.getUser().isBot()) {
                        if (data.getVerifChannel().getId().equalsIgnoreCase(event.getChannel().getId())) {
                            if (event.getMessageId().equalsIgnoreCase(data.getVerif_messageId())) {
                                if (event.getReactionEmote().getName().equalsIgnoreCase(data.getVerif_reactionUnicode())) {
                                    if (data.getRulesRole() != null) {
                                        if (Utils.hasRole(m, data.getRulesRole())) {
                                            event.getReaction().removeReaction(m.getUser()).queue();
                                            return;
                                        }
                                    }
                                    if (data.getVerifRole() != null) {
                                        if (!Utils.hasRole(m, data.getVerifRole())) {
                                            event.getGuild().addRoleToMember(m, data.getVerifRole()).queue();
                                            if (data.hasVerifRemovableRole()) {
                                                if (Utils.hasRole(m, data.getVerifRoleToBeRemoved())) {
                                                    event.getGuild().removeRoleFromMember(m, data.getVerifRoleToBeRemoved()).queue();
                                                }
                                            }
                                        }
                                        event.getReaction().removeReaction(m.getUser()).queue();
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
        if (data.getRulesEnabled()) {
            if (data.getRulesChannel() != null) {
                event.retrieveMember().queue((m) -> {
                    if (!m.getUser().isBot()) {
                        if (data.getRulesChannel().getId().equalsIgnoreCase(event.getChannel().getId())) {
                            if (event.getMessageId().equalsIgnoreCase(data.getRules_messageId())) {
                                if (event.getReactionEmote().getName().equalsIgnoreCase(data.getRules_reactionUnicode())) {
                                    if (data.getRulesRole() != null) {
                                        if (!Utils.hasRole(m, data.getRulesRole())) {
                                            event.getGuild().addRoleToMember(m, data.getRulesRole()).queue();
                                            if (data.hasRulesRemovableRole()) {
                                                if (Utils.hasRole(m, data.getRulesRoleToBeRemoved())) {
                                                    event.getGuild().removeRoleFromMember(m, data.getRulesRoleToBeRemoved()).queue();
                                                }
                                            }
                                        }
                                        event.getReaction().removeReaction(m.getUser()).queue();
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
    }

}
