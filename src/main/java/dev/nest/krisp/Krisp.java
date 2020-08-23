package dev.nest.krisp;

import com.zaxxer.hikari.HikariDataSource;
import dev.nest.krisp.cmds.*;
import dev.nest.krisp.listeners.GuildJoinListener;
import dev.nest.krisp.listeners.GuildLeaveListener;
import dev.nest.krisp.listeners.ReactionAddListener;
import dev.nest.krisp.objects.*;
import dev.nest.krisp.threads.StatusThread;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Krisp {

    private static JDA jda;

    private static DBConfigData configData;
    private static FileManager fileManager;
    private static ConnectionHandler handler;
    private static DataManager dataManager;

    public static void main(String[] args) {
        try {
            configData = new DBConfigData("loremipsum", "loremipsum", "loremipsum", "loremipsum", 3306, "null");
            fileManager = new FileManager(configData);
            jda = JDABuilder.createDefault(configData.getToken()).build();
            DataSource dataSource = new DataSource(configData);
            dataManager = new DataManager();
            handler = new ConnectionHandler(new HikariDataSource(dataSource.getHikariConfig()));
            jda.addEventListener(new InfoCmd());
            jda.addEventListener(new HelpCmd());
            jda.addEventListener(new VerifyCmd());
            jda.addEventListener(new ReactionAddListener());
            jda.addEventListener(new PrefixCmd());
            jda.addEventListener(new GuildJoinListener());
            jda.addEventListener(new GuildLeaveListener());
            jda.addEventListener(new RulesCmd());
            jda.awaitReady();
            StatusThread.run();
        } catch (LoginException | InterruptedException exception) {
            exception.printStackTrace();
            System.exit(-1);
        }
    }

    public static ConnectionHandler getHandler() {
        return handler;
    }

    public static DBConfigData getConfigData() {
        return configData;
    }

    public static DataManager getDataManager() {
        return dataManager;
    }

    public static FileManager getFileManager() {
        return fileManager;
    }

    public static JDA getJDA() {
        return jda;
    }

}
