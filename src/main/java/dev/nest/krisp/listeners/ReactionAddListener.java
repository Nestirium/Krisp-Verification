package dev.nest.krisp.listeners;

import dev.nest.krisp.Krisp;
import dev.nest.krisp.objects.RulesData;
import dev.nest.krisp.objects.VerificationData;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReactionAddListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        VerificationData data = Krisp.getVerifDataManager().getData(event.getGuild().getId());
        RulesData rulesData = Krisp.getRuleDataManager().getData(event.getGuild().getId());
        if (data.isEnabled()) {
            if (data.getChannel() != null) {
                event.retrieveMember().queue((m) -> {
                    if (!m.getUser().isBot()) {
                        if (data.getChannel().getId().equalsIgnoreCase(event.getChannel().getId())) {
                            if (event.getMessageId().equalsIgnoreCase(data.getMessageId())) {
                                if (event.getReactionEmote().getName().equalsIgnoreCase(data.getReactionUnicode())) {
                                    if (data.getRole() != null) {
                                        if (!hasRole(m, data.getRole())) {
                                            event.getGuild().addRoleToMember(m, data.getRole()).queue();
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
        if (rulesData.isEnabled()) {
            if (rulesData.getChannel() != null) {
                event.retrieveMember().queue((m) -> {
                    if (!m.getUser().isBot()) {
                        if (rulesData.getChannel().getId().equalsIgnoreCase(event.getChannel().getId())) {
                            if (event.getMessageId().equalsIgnoreCase(rulesData.getMessageId())) {
                                if (event.getReactionEmote().getName().equalsIgnoreCase(rulesData.getReactionUnicode())) {
                                    if (rulesData.getRole() != null) {
                                        if (!hasRole(m, rulesData.getRole())) {
                                            event.getGuild().addRoleToMember(m, rulesData.getRole()).queue();
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
            if (r.getId().equalsIgnoreCase(role.getId())) {
                return true;
            }
        }
        return false;
    }



}
