package shwendel.reallyawesomecontests.contest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import shwendel.reallyawesomecontests.ReallyAwesomeContests;
import shwendel.reallyawesomecontests.config.ConfigManager;
import shwendel.reallyawesomecontests.config.ConfigType;
import shwendel.reallyawesomecontests.util.TimeUtil;

import java.util.*;

public class ContestManager {

    private static LinkedHashMap<UUID, Contest> contests = new LinkedHashMap<>();
    private static Map<String, ContestType> contestTypes = new HashMap<>();
    private static ReallyAwesomeContests instance = ReallyAwesomeContests.getInstance();

    static {
        FileConfiguration config = ConfigManager.getConfigs().get(ConfigType.CONFIG).getConfig();

        for(String contestType : config.getConfigurationSection("contests").getKeys(false)) {

            String configNotation = "contests." + contestType;
            String bossBarNotation = configNotation + ".boss_bar";
            boolean bossBarEnabled = config.getBoolean(bossBarNotation + ".enabled");

            contestTypes.put(contestType, new ContestType(
                    contestType,
                    config.getStringList(configNotation + ".description").toArray(new String[0]),
                    TimeUtil.timeFormatToMillis(config.getString(configNotation + ".length")),
                    TimeUtil.timeFormatToMillis(config.getString(configNotation + ".starts_every")),
                    ContestAction.valueOf(config.getString(configNotation + ".action")),
                    new HashMap<>(),
                    bossBarEnabled ? ChatColor.translateAlternateColorCodes('&', config.getString(bossBarNotation + ".text")) : null,
                    bossBarEnabled ? BarColor.valueOf(config.getString(bossBarNotation + ".color")) : null
            ));

        }

        for(ContestType contestType : contestTypes.values()) {
            System.out.println("Test2");
            scheduleContest(contestType);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                tick();
            }
        }.runTaskTimer(instance, 0, 20);

    }

    public static void scheduleContest(ContestType type) {

        Bukkit.broadcastMessage("Scheduled contest for" + type.getAutoRenew() + "f rom now :-)");

        new BukkitRunnable() {

            @Override
            public void run() {
                addContest(new Contest(type));
            }

        }.runTaskLater(instance, type.getAutoRenew() / 50);

    }

    public static void tick() {
        System.out.println(contests.values() + " Before clearing");
        clearEndedContests();
        System.out.println(contests.values() + " After clearing");
        for(Contest contest : contests.values()) {
            System.out.println(contest);
            contest.tick();
        }
    }

    public static void addContest(Contest contest) {
        // Announce contest
        contests.put(contest.getUUID(), contest);
    }

    public static Contest getLatestContest() {
        List<UUID> reversed = new ArrayList<UUID>(contests.keySet());

        for(UUID uuid : reversed) {
            Contest contest = contests.get(uuid);

            return contest;

        }

        return null;
    }

    private static void clearEndedContests() {
        List<UUID> uuids = new ArrayList<>();

        contests.forEach((uuid, contest) -> {
            if(contest.isContestOver()) {
                uuids.add(uuid);
            }
        });

        for(UUID uuid : uuids) {
            contests.remove(uuid);
        }

    }

    public static LinkedHashMap<UUID, Contest> getContests() {
        return contests;
    }

}
