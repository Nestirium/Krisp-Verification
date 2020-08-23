package dev.nest.krisp.objects;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {

    public FileManager(DBConfigData data) {
        if (!Files.exists(Paths.get("C:\\Users\\Nestirium\\Desktop\\Krisp Verification\\database.cfg"))) {
            File dir = new File("C:\\Users\\Nestirium\\Desktop\\", "Krisp Verification");
            File file = new File("C:\\Users\\Nestirium\\Desktop\\Krisp Verification\\", "database.cfg");
            if (dir.mkdirs()) {
                System.out.println("Recursively created directories for database.cfg file!");
            }
            try {
                if (file.createNewFile()) {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    /**
                     * In this case, we know that a database file didn't exist so we create a new file and write default data passed by the constructor to it.
                     */
                    writer.write("token=" + data.getToken() + "\n");
                    writer.write("database=" + data.getDatabase() + "\n");
                    writer.write("username=" + data.getUsername() + "\n");
                    writer.write("password=" + data.getPassword() + "\n");
                    writer.write("host=" + data.getHost() + "\n");
                    writer.write("port=" + data.getPort() + "\n");
                    writer.flush();
                    writer.close();
                    System.out.println("Successfully created new database.cfg file!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            File file = new File("C:\\Users\\Nestirium\\Desktop\\Krisp Verification", "database.cfg");
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String[] line1 = reader.readLine().split("=");
                String[] line2 = reader.readLine().split("=");
                String[] line3 = reader.readLine().split("=");
                String[] line4 = reader.readLine().split("=");
                String[] line5 = reader.readLine().split("=");
                String[] line6 = reader.readLine().split("=");
                if (line1.length < 2) {
                    data.setToken("null");
                } else {
                    data.setToken(line1[1]);
                }
                if (line2.length < 2) {
                    data.setDatabase(null);
                } else {
                    data.setDatabase(line2[1]);
                }
                if (line3.length < 2) {
                    data.setUsername(null);
                } else {
                    data.setUsername(line3[1]);
                }
                if (line4.length < 2) {
                    data.setPassword(null);
                } else {
                    data.setPassword(line4[1]);
                }
                if (line5.length < 2) {
                    data.setHost(null);
                } else {
                    data.setHost(line5[1]);
                }
                if (line6.length < 2) {
                    data.setPort(0);
                } else {
                    data.setPort(Integer.parseInt(line6[1]));
                }
                System.out.println("Found existing database.cfg file.");
                /**
                 * In this case, we know that a data file already exists, so we read it and update the default data passed by the constructor with the new read data.
                 */
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
