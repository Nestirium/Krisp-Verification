package dev.nest.krisp.threads;

import dev.nest.krisp.Krisp;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StatusThread {

    private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    public static void run() {
        service.scheduleAtFixedRate(() -> {
            Random random = new Random();
            String[] statuses = {"ONLINE", "DO_NOT_DISTURB", "IDLE"};
            String[] listening = {"Wadi3 el she3", "Omak", "ya 7osen", "Aba 3abd El Lah"};
            String[] games = {"with your mom", "with your dad", "with myself", "with Nestirium"};
            boolean isPlaying = random.nextBoolean();
            int randomGames = random.nextInt(games.length);
            int randomStatus = random.nextInt(statuses.length);
            int randomListening = random.nextInt(listening.length);
            Krisp.getJDA().getPresence().setStatus(OnlineStatus.fromKey(statuses[randomStatus]));
            if (isPlaying) {
                Krisp.getJDA().getPresence().setActivity(Activity.playing(games[randomGames]));
            } else {
                Krisp.getJDA().getPresence().setActivity(Activity.listening(listening[randomListening]));
            }
        }, 60, 60, TimeUnit.SECONDS);
    }

    public static void terminate() {
        service.shutdown();
    }

}
