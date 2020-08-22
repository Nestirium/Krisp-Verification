package dev.nest.krisp.managers;

import dev.nest.krisp.Krisp;
import dev.nest.krisp.enums.DataType;
import dev.nest.krisp.objects.GenericData;
import dev.nest.krisp.objects.RulesData;
import dev.nest.krisp.objects.VerificationData;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public FileManager() {
        initSetup();
        readData();
    }

    public void initSetup() {
        if (!Files.exists(Paths.get("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\"))) {
            File dataDir = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\", "data");
            try {
                if (dataDir.mkdirs()) {
                    System.out.println("Successfully created new data directory for Krisp Verification");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setupData(String guildId) {
        Guild guild = Krisp.getJDA().getGuildById(guildId);
        if (guild != null) {
            if (!Files.exists(Paths.get("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId()))) {
                File guildDir = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\", guild.getId());
                try {
                    if (guildDir.mkdirs()) {
                        System.out.println("Successfully created new guild directory for " + guild.getId());
                        File genericData = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId(), "genericcache.dat");
                        if (genericData.createNewFile()) {
                            System.out.println("Successfully created new genericcache.dat for " + guild.getId());
                        }
                        File verificationData = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId(), "verifcache.dat");
                        if (verificationData.createNewFile()) {
                            System.out.println("Successfully created new verifcache.dat for " + guild.getId());
                        }
                        File rulesData = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId(), "rulescache.dat");
                        if (rulesData.createNewFile()) {
                            System.out.println("Successfully created new rulescache.dat for " + guild.getId());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void writeData(String guildId, DataType type, boolean isNew) {
        Guild guild = Krisp.getJDA().getGuildById(guildId);
        if (guild != null) {
            FileWriter fileWriter;
            BufferedWriter writer;
            String verifData, ruleData, genericData;
            if (!isNew) {
                switch (type) {
                    case VERIF:
                        verifData = Krisp.getVerifDataManager().getData(guildId).toString();
                        if (!Files.exists(Paths.get("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId() + "\\verifcache.dat"))) {
                            File verificationData = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId(), "verifcache.dat");
                            try {
                                if (verificationData.mkdirs() && verificationData.createNewFile()) {
                                    System.out.println("Successfully created new verifcache.dat for " + guild.getId() + " file while attempting to write to it");
                                    fileWriter = new FileWriter(verificationData);
                                    writer = new BufferedWriter(fileWriter);
                                    writer.write(verifData);
                                    writer.flush();
                                    writer.close();
                                    fileWriter.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            File verificationData = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId(), "verifcache.dat");
                            try {
                                fileWriter = new FileWriter(verificationData);
                                writer = new BufferedWriter(fileWriter);
                                writer.write(verifData);
                                writer.flush();
                                writer.close();
                                fileWriter.close();
                                System.out.println("Found existing verifcache.dat file for " + guild.getId() + " and done writing to it.");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case RULES:
                        ruleData = Krisp.getRuleDataManager().getData(guildId).toString();
                        if (!Files.exists(Paths.get("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId() + "\\rulescache.dat"))) {
                            File rulesData = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId(), "rulescache.dat");
                            try {
                                if (rulesData.mkdirs() && rulesData.createNewFile()) {
                                    System.out.println("Successfully created new rulescache.dat for " + guild.getId() + " file while attempting to write to it");
                                    fileWriter = new FileWriter(rulesData);
                                    writer = new BufferedWriter(fileWriter);
                                    writer.write(ruleData);
                                    writer.flush();
                                    writer.close();
                                    fileWriter.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            File rulesData = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId(), "rulescache.dat");
                            try {
                                fileWriter = new FileWriter(rulesData);
                                writer = new BufferedWriter(fileWriter);
                                writer.write(ruleData);
                                writer.flush();
                                writer.close();
                                fileWriter.close();
                                System.out.println("Found existing rulescache.dat file for " + guild.getId() + " and done writing to it.");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case GENERIC:
                        genericData = Krisp.getGenericDataManager().getData(guildId).toString();
                        if (!Files.exists(Paths.get("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId() + "\\genericcache.dat"))) {
                            File genericDat = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId(), "genericcache.dat");
                            try {
                                if (genericDat.mkdirs() && genericDat.createNewFile()) {
                                    System.out.println("Successfully created new genericcache.dat for " + guild.getId() + " file while attempting to write to it");
                                    fileWriter = new FileWriter(genericDat);
                                    writer = new BufferedWriter(fileWriter);
                                    writer.write(genericData);
                                    writer.flush();
                                    writer.close();
                                    fileWriter.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            File genericDat = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId(), "genericcache.dat");
                            try {
                                fileWriter = new FileWriter(genericDat);
                                writer = new BufferedWriter(fileWriter);
                                writer.write(genericData);
                                writer.flush();
                                writer.close();
                                fileWriter.close();
                                System.out.println("Found existing genericcache.dat file for " + guild.getId() + " and done writing to it.");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
            } else {
                verifData = new VerificationData(guild.getId()).toString();
                ruleData = new RulesData(guild.getId()).toString();
                genericData = new GenericData("~", guild.getId()).toString();
                if (!Files.exists(Paths.get("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId() + "\\verifcache.dat"))) {
                    File verificationData = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId(), "verifcache.dat");
                    try {
                        if (verificationData.mkdirs() && verificationData.createNewFile()) {
                            System.out.println("Successfully created new verifcache.dat for " + guild.getId() + " file while attempting to write to it");
                            fileWriter = new FileWriter(verificationData);
                            writer = new BufferedWriter(fileWriter);
                            writer.write(verifData);
                            writer.flush();
                            writer.close();
                            fileWriter.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    File verificationData = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId(), "verifcache.dat");
                    try {
                        fileWriter = new FileWriter(verificationData);
                        writer = new BufferedWriter(fileWriter);
                        writer.write(verifData);
                        writer.flush();
                        writer.close();
                        fileWriter.close();
                        System.out.println("Found existing verifcache.dat file for " + guild.getId() + " and done writing to it.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!Files.exists(Paths.get("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId() + "\\rulescache.dat"))) {
                    File rulesData = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId(), "rulescache.dat");
                    try {
                        if (rulesData.mkdirs() && rulesData.createNewFile()) {
                            System.out.println("Successfully created new rulescache.dat for " + guild.getId() + " file while attempting to write to it");
                            fileWriter = new FileWriter(rulesData);
                            writer = new BufferedWriter(fileWriter);
                            writer.write(ruleData);
                            writer.flush();
                            writer.close();
                            fileWriter.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    File rulesData = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId(), "rulescache.dat");
                    try {
                        fileWriter = new FileWriter(rulesData);
                        writer = new BufferedWriter(fileWriter);
                        writer.write(ruleData);
                        writer.flush();
                        writer.close();
                        fileWriter.close();
                        System.out.println("Found existing rulescache.dat file for " + guild.getId() + " and done writing to it.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!Files.exists(Paths.get("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId() + "\\genericcache.dat"))) {
                    File genericDat = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId(), "genericcache.dat");
                    try {
                        if (genericDat.mkdirs() && genericDat.createNewFile()) {
                            System.out.println("Successfully created new genericcache.dat for " + guild.getId() + " file while attempting to write to it");
                            fileWriter = new FileWriter(genericDat);
                            writer = new BufferedWriter(fileWriter);
                            writer.write(genericData);
                            writer.flush();
                            writer.close();
                            fileWriter.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    File genericDat = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId(), "genericcache.dat");
                    try {
                        fileWriter = new FileWriter(genericDat);
                        writer = new BufferedWriter(fileWriter);
                        writer.write(genericData);
                        writer.flush();
                        writer.close();
                        fileWriter.close();
                        System.out.println("Found existing genericcache.dat file for " + guild.getId() + " and done writing to it.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void deleteGuildData(String guildId) {
        try {
            File file = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guildId);
            if (file.isDirectory()) {
                FileUtils.deleteDirectory(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readData() {
        if (Files.exists(Paths.get("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\"))) {
            File dataDir = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\", "data");
            File[] guildDirs = dataDir.listFiles();
            if (guildDirs != null) {
                File guildDir;
                File[] files;
                FileReader fileReader;
                BufferedReader reader;
                String result, message, url;
                String[] data;
                Guild guild;
                for (File file : guildDirs) {
                    guild = Krisp.getJDA().getGuildById(file.getName());
                    if (guild != null) {
                        guildDir = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\", guild.getId());
                        files = guildDir.listFiles();
                        if (files != null) {
                            for (File file1 : files) {
                                if (file1.getName().equalsIgnoreCase("verifcache.dat")) {
                                    File verificationData = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId() + "\\", "verifcache.dat");
                                    new VerificationData(guild.getId());
                                    try {
                                        fileReader = new FileReader(verificationData);
                                        reader = new BufferedReader(fileReader);
                                        result = reader.readLine();
                                        data = result.split(" ");
                                        url = reader.readLine();
                                        String line;
                                        List<String> lines = new ArrayList<>();
                                        while ((line = reader.readLine()) != null) {
                                            lines.add(line);
                                        }
                                        System.out.println(lines);
                                        message = "dummy";
                                        reader.close();
                                        fileReader.close();
                                        Krisp.getVerifDataManager().getData(guild.getId()).setEnabled(Boolean.parseBoolean(data[0]));
                                        Krisp.getVerifDataManager().getData(guild.getId()).setChannelId(data[1]);
                                        Krisp.getVerifDataManager().getData(guild.getId()).setReactionUnicode(data[2]);
                                        Krisp.getVerifDataManager().getData(guild.getId()).setRoleId(data[3]);
                                        Krisp.getVerifDataManager().getData(guild.getId()).setMessage(message);
                                        Krisp.getVerifDataManager().getData(guild.getId()).setImageURL(url);
                                        Krisp.getVerifDataManager().getData(guild.getId()).setMessageId(data[4]);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (file1.getName().equalsIgnoreCase("rulescache.dat")) {
                                    File rulesData = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId() + "\\", "rulescache.dat");
                                    new RulesData(guild.getId());
                                    try {
                                        fileReader = new FileReader(rulesData);
                                        reader = new BufferedReader(fileReader);
                                        result = reader.readLine();
                                        data = result.split(" ");
                                        url = reader.readLine();
                                        String line;
                                        StringBuilder builder = new StringBuilder();
                                        while ((line = reader.readLine()) != null) {
                                            builder.append(line);
                                        }
                                        message = builder.toString();
                                        reader.close();
                                        fileReader.close();
                                        Krisp.getRuleDataManager().getData(guild.getId()).setEnabled(Boolean.parseBoolean(data[0]));
                                        Krisp.getRuleDataManager().getData(guild.getId()).setChannelId(data[1]);
                                        Krisp.getRuleDataManager().getData(guild.getId()).setReactionUnicode(data[2]);
                                        Krisp.getRuleDataManager().getData(guild.getId()).setRoleId(data[3]);
                                        Krisp.getRuleDataManager().getData(guild.getId()).setMessage(message);
                                        Krisp.getRuleDataManager().getData(guild.getId()).setImageURL(url);
                                        Krisp.getRuleDataManager().getData(guild.getId()).setMessageId(data[4]);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (file1.getName().equalsIgnoreCase("genericcache.dat")) {
                                    File genericData = new File("C:\\Users\\Nestirium\\Desktop\\Discord Bots\\Krisp Verification\\data\\" + guild.getId() + "\\", "genericcache.dat");
                                    try {
                                        fileReader = new FileReader(genericData);
                                        reader = new BufferedReader(fileReader);
                                        String prefix = reader.readLine();
                                        reader.close();
                                        fileReader.close();
                                        new GenericData(prefix, guild.getId());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    } else {
                        try {
                            if (file.delete()) {
                                System.out.println("Deleted invalid guild directory " + file.getName());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
