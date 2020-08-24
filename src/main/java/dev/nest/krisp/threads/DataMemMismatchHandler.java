package dev.nest.krisp.threads;

import dev.nest.krisp.Krisp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DataMemMismatchHandler {

    private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    public static void run() {
        service.scheduleAtFixedRate(() -> Krisp.getHandler().validateData(false, false), 60, 60, TimeUnit.SECONDS);
    }

    public static void terminate() {
        service.shutdown();
    }

}
