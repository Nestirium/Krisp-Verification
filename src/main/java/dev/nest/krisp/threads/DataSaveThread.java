package dev.nest.krisp.threads;

import dev.nest.krisp.Krisp;
import dev.nest.krisp.enums.DataType;
import dev.nest.krisp.objects.GenericData;
import dev.nest.krisp.objects.RulesData;
import dev.nest.krisp.objects.VerificationData;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DataSaveThread {


    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public static void run() {

        executor.scheduleAtFixedRate(() -> {
            for (VerificationData data : Krisp.getVerifDataManager().getDataMap().values()) {
                Krisp.getFileManager().writeData(data.getGuildId(), DataType.VERIF, false);
            }
            for (RulesData data : Krisp.getRuleDataManager().getDataMap().values()) {
                Krisp.getFileManager().writeData(data.getGuildId(), DataType.RULES, false);
            }
            for (GenericData data : Krisp.getGenericDataManager().getDataMap().values()) {
                Krisp.getFileManager().writeData(data.getGuildId(), DataType.GENERIC, false);
            }
        }, 1, 2, TimeUnit.SECONDS);
    }

    public static void terminate() {
        executor.shutdown();
    }


}
