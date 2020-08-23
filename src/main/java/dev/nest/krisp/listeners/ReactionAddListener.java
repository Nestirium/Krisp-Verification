package dev.nest.krisp.listeners;

import dev.nest.krisp.Krisp;
import dev.nest.krisp.objects.DataStorage;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
                                    if (data.getVerifRole() != null) {
                                        if (!hasRole(m, data.getVerifRole())) {
                                            event.getGuild().addRoleToMember(m, data.getVerifRole()).queue();
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
                                        if (!hasRole(m, data.getRulesRole())) {
                                            event.getGuild().addRoleToMember(m, data.getRulesRole()).queue();
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

    public boolean hasRole(Member member, Role role) {
        List<Role> roles = member.getRoles();
        for (Role r : roles) {
            if (!r.getId().equalsIgnoreCase(role.getId())) {
                continue;
            }
            return true;
        }
        return false;
    }



}
