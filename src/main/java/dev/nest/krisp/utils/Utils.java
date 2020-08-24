package dev.nest.krisp.utils;

import dev.nest.krisp.Krisp;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Utils {

    public static boolean isURL(String input) {
        try {
            new URL(input);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public static boolean isPrefix(String guildId, String input) {
        try {
            char a = input.charAt(0);
            String prefix = Krisp.getDataManager().getData(guildId).getPrefix();
            char b = prefix.charAt(0);
            return a == b;
        } catch (StringIndexOutOfBoundsException e) {
            return false;
        }
    }

    public static String checkReactionLength(String reaction) {
        if (reaction.length() > 1) {
            return reaction.replaceAll(reaction.substring(1), "");
        }
        return reaction;
    }

    public static int toInt(boolean b) {
        if (b) {
            return 1;
        } else {
            return 0;
        }
    }

    public static boolean hasRole(Member member, Role role) {
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
