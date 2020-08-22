package dev.nest.krisp.utils;

import dev.nest.krisp.Krisp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
            String prefix = Krisp.getGenericDataManager().getData(guildId).getPrefix();
            char b = prefix.charAt(0);
            return a == b;
        } catch (StringIndexOutOfBoundsException e) {
            return false;
        }
    }

    public static String stripPrefix(String guildId, String input) {
        return input.replaceFirst(Krisp.getGenericDataManager().getData(guildId).getPrefix(), "");
    }

    public static String checkReactionLength(String reaction) {
        if (reaction.length() > 1) {
            return reaction.replaceAll(reaction.substring(1), "");
        }
        return reaction;
    }

}
