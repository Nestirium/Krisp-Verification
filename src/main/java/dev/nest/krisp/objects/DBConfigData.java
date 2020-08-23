package dev.nest.krisp.objects;


public class DBConfigData {

    private String database, username, password, host, token;
    private int port;

    public DBConfigData(String database,
                        String username,
                        String password,
                        String host,
                        int port,
                        String token) {
        this.database = database;
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password =  password;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
