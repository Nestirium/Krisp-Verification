package dev.nest.krisp;

import dev.nest.krisp.cmds.*;
import dev.nest.krisp.listeners.GuildJoinListener;
import dev.nest.krisp.listeners.GuildLeaveListener;
import dev.nest.krisp.listeners.ReactionAddListener;
import dev.nest.krisp.managers.FileManager;
import dev.nest.krisp.managers.GenericDataManager;
import dev.nest.krisp.managers.RuleDataManager;
import dev.nest.krisp.managers.VerifDataManager;
import dev.nest.krisp.threads.DataSaveThread;
import dev.nest.krisp.threads.StatusThread;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;


public class Krisp {

    private static JDA jda;
    private static VerifDataManager verifDataManager;
    private static RuleDataManager ruleDataManager;
    private static GenericDataManager genericDataManager;
    private static FileManager fileManager;
    private static final String TOKEN = "";

    public static void main(String[] args) {
        try {
            jda = JDABuilder.createDefault(TOKEN).build();
            jda.addEventListener(new InfoCmd());
            jda.addEventListener(new HelpCmd());
            jda.addEventListener(new VerifyCmd());
            jda.addEventListener(new ReactionAddListener());
            jda.addEventListener(new PrefixCmd());
            jda.addEventListener(new GuildJoinListener());
            jda.addEventListener(new GuildLeaveListener());
            jda.addEventListener(new RulesCmd());
            jda.awaitReady();
            genericDataManager = new GenericDataManager();
            verifDataManager = new VerifDataManager();
            ruleDataManager = new RuleDataManager();
            fileManager = new FileManager();
            DataSaveThread.run();
            StatusThread.run();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static GenericDataManager getGenericDataManager() {
        return genericDataManager;
    }

    public static VerifDataManager getVerifDataManager() {
        return verifDataManager;
    }

    public static RuleDataManager getRuleDataManager() {
        return ruleDataManager;
    }

    public static FileManager getFileManager() {
        return fileManager;
    }

    public static JDA getJDA() {
        return jda;
    }

}
