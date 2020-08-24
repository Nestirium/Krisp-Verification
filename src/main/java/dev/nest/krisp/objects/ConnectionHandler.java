package dev.nest.krisp.objects;

import com.zaxxer.hikari.HikariDataSource;
import dev.nest.krisp.Krisp;
import dev.nest.krisp.utils.Utils;
import net.dv8tion.jda.api.entities.Guild;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ConnectionHandler {

    private final HikariDataSource dataSource;

    public ConnectionHandler(HikariDataSource dataSource) {
        this.dataSource = dataSource;
        CompletableFuture.runAsync(() -> {
            try (Connection connection = dataSource.getConnection()) {
                String sql = "CREATE TABLE IF NOT EXISTS GUILDS" +
                        "(id INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                        "guild_id VARCHAR(100) NOT NULL," +
                        "prefix VARCHAR(100)," +
                        "restrictedChannelId VARCHAR(100)," +
                        "minimumRoleId VARCHAR(100)," +
                        "enabled_verif BOOLEAN," +
                        "channelId_verif VARCHAR(100)," +
                        "reactionUnicode_verif VARCHAR(100)," +
                        "roleId_verif VARCHAR(100)," +
                        "roleToBeRemoved_verif VARCHAR(100)," +
                        "messageId_verif VARCHAR(100)," +
                        "imageURL_verif VARCHAR(500)," +
                        "enabled_rules BOOLEAN," +
                        "channelId_rules VARCHAR(100)," +
                        "reactionUnicode_rules VARCHAR(100)," +
                        "roleId_rules VARCHAR(100)," +
                        "roleToBeRemoved_rules VARCHAR(100)," +
                        "messageId_rules VARCHAR(100)," +
                        "imageURL_rules VARCHAR(500))";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                readData();
                validateData(true, true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void validateData(boolean await, boolean async) {
        if (await) {
            try {
                Krisp.getJDA().awaitReady();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (async) {
            CompletableFuture.runAsync(() -> {
                List<Guild> guilds = Krisp.getJDA().getGuilds();
                try (Connection connection = dataSource.getConnection()) {
                    for (Guild guild : guilds) {
                        String sql = String.format("SELECT id FROM GUILDS WHERE guild_id='%s'", guild.getId());
                        try (PreparedStatement statement = connection.prepareStatement(sql)) {
                            if (statement.executeQuery().next()) {
                                continue;
                            }
                            new DataStorage("~", guild.getId());
                            writeData(guild.getId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } else {
            List<Guild> guilds = Krisp.getJDA().getGuilds();
            try (Connection connection = dataSource.getConnection()) {
                for (Guild guild : guilds) {
                    String sql = String.format("SELECT id FROM GUILDS WHERE guild_id='%s'", guild.getId());
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        if (statement.executeQuery().next()) {
                            continue;
                        }
                        new DataStorage("~", guild.getId());
                        writeData(guild.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteData(String guildId) {
        CompletableFuture.runAsync(() -> {
           try (Connection connection = dataSource.getConnection()) {
               String sql = String.format("DELETE FROM GUILDS WHERE guild_id='%s'", guildId);
               try (PreparedStatement statement = connection.prepareStatement(sql)) {
                   statement.executeUpdate();
               } catch (Exception e) {
                   e.printStackTrace();
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
        });
    }

    public void readData() {
        CompletableFuture.runAsync(() -> {
            try (Connection connection = dataSource.getConnection()) {
                String sql = "SELECT * FROM GUILDS";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        DataStorage s = new DataStorage(resultSet.getString("prefix"), resultSet.getString("guild_id"));
                        s.setRestrictedChannelId(resultSet.getString("restrictedChannelId"));
                        s.setMinimumRoleId(resultSet.getString("minimumRoleId"));
                        s.setVerifEnabled(resultSet.getBoolean("enabled_verif"));
                        s.setVerif_channelId(resultSet.getString("channelId_verif"));
                        s.setVerif_reactionUnicode(resultSet.getString("reactionUnicode_verif"));
                        s.setVerif_roleId(resultSet.getString("roleId_verif"));
                        s.setVerif_roleToBeRemoved(resultSet.getString("roleToBeRemoved_verif"));
                        s.setVerif_messageId(resultSet.getString("messageId_verif"));
                        try {
                            s.setVerif_imageURL(resultSet.getString("imageURL_verif"));
                        } catch (MalformedURLException ignored) {}
                        s.setRulesEnabled(resultSet.getBoolean("enabled_rules"));
                        s.setRules_channelId(resultSet.getString("channelId_rules"));
                        s.setRules_reactionUnicode(resultSet.getString("reactionUnicode_rules"));
                        s.setRules_roleId(resultSet.getString("roleId_rules"));
                        s.setVerif_roleToBeRemoved(resultSet.getString("roleToBeRemoved_rules"));
                        s.setRules_messageId(resultSet.getString("messageId_rules"));
                        try {
                            s.setRules_imageURL(resultSet.getString("imageURL_rules"));
                        } catch (MalformedURLException ignored) {}
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void writeData(String guildId) {
        Guild guild = Krisp.getJDA().getGuildById(guildId);
        if (guild != null) {
            String[] data = Krisp.getDataManager().getData(guild.getId()).toString().split(" ");
            CompletableFuture.runAsync(() -> {
                try (Connection connection = dataSource.getConnection()) {
                    String sql = "INSERT INTO GUILDS(guild_id, restrictedChannelId, minimumRoleId, prefix, enabled_verif, channelId_verif, reactionUnicode_verif, roleId_verif, roleToBeRemoved_verif, messageId_verif, imageURL_verif, enabled_rules, channelId_rules, reactionUnicode_rules, roleId_rules, roleToBeRemoved_rules, messageId_rules, imageURL_rules) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, guild.getId());
                        statement.setString(2, data[0]);
                        statement.setString(3, data[1]);
                        statement.setString(4, data[2]);
                        statement.setBoolean(5, Boolean.parseBoolean(data[3]));
                        statement.setString(6, data[4]);
                        statement.setString(7, data[5]);
                        statement.setString(8, data[6]);
                        statement.setString(9, data[7]);
                        statement.setString(10, data[8]);
                        statement.setString(11, data[9]);
                        statement.setBoolean(12, Boolean.parseBoolean(data[10]));
                        statement.setString(13, data[11]);
                        statement.setString(14, data[12]);
                        statement.setString(15, data[13]);
                        statement.setString(16, data[14]);
                        statement.setString(17, data[15]);
                        statement.setString(18, data[16]);
                        statement.executeUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void saveData(String guildId) {
        Guild guild = Krisp.getJDA().getGuildById(guildId);
        if (guild != null) {
            String[] data = Krisp.getDataManager().getData(guild.getId()).toString().split(" ");
            CompletableFuture.runAsync(() -> {
                try (Connection connection = dataSource.getConnection()) {
                    String sql =
                            String.format("UPDATE GUILDS SET restrictedChannelId='%s', minimumRoleId='%s', prefix='%s', enabled_verif='%s', channelId_verif='%s', reactionUnicode_verif='%s', roleId_verif='%s', roleToBeRemoved_verif='%s', messageId_verif='%s', imageURL_verif='%s', enabled_rules='%s', channelId_rules='%s', reactionUnicode_rules='%s', roleId_rules='%s', roleToBeRemoved_rules='%s', messageId_rules='%s', imageURL_rules='%s' WHERE guild_id='%s'", data[0], data[1], data[2], Utils.toInt(Boolean.parseBoolean(data[3])), data[4], data[5], data[6], data[7], data[8], data[9], Utils.toInt(Boolean.parseBoolean(data[10])), data[11], data[12], data[13], data[14], data[15], data[16], guild.getId());
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.executeUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }


    public HikariDataSource getDataSource() {
        return dataSource;
    }

}
