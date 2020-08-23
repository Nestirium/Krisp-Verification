package dev.nest.krisp.objects;

import com.zaxxer.hikari.HikariConfig;

public class DataSource {

    private final HikariConfig hikariConfig;

    public DataSource(DBConfigData data) {
        hikariConfig = new HikariConfig();
        hikariConfig.setUsername(data.getUsername());
        hikariConfig.setPassword(data.getPassword());
        hikariConfig.setJdbcUrl(String.format("jdbc:mysql://%s/%s", data.getHost(), data.getDatabase()));
        hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        hikariConfig.setMaximumPoolSize(3);
        hikariConfig.addDataSourceProperty("cachePrepStmts" , "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize" , "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit" , "2048");
        hikariConfig.addDataSourceProperty("serverName", data.getHost());
        hikariConfig.addDataSourceProperty("port", data.getPort());
        hikariConfig.addDataSourceProperty("databaseName", data.getDatabase());
    }

    public HikariConfig getHikariConfig() {
        return hikariConfig;
    }

}
