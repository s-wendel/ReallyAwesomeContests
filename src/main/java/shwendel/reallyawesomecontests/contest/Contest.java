package shwendel.reallyawesomecontests.contest;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;
import shwendel.reallyawesomecontests.ReallyAwesomeContests;
import shwendel.reallyawesomecontests.util.TimeUtil;

import java.util.*;

public class Contest {

    private UUID uuid;
    private ContestType type;
    private LinkedHashMap<UUID, Integer> values; // Oh it's the values of all the participants!!!!
    private long contestStart; // Time in millis when it started
    private Map<UUID, BossBar> bossBars;
    private int tickIntervals;

    public Contest(ContestType type) {
        this.type = type;
        this.uuid = UUID.randomUUID();
        this.values = new LinkedHashMap<>();
        this.contestStart = System.currentTimeMillis();
        this.bossBars = new HashMap<>();
        ContestManager.scheduleContest(type);
        this.tickIntervals = 5;
    }

    public void addProgress(UUID uuid, int value) {
        values.put(uuid, values.getOrDefault(uuid, 0) + value);
    }

    public void tick() {

        if(tickIntervals == 5) {

            LinkedHashMap<UUID, Integer> sortedValues = new LinkedHashMap<>();

            values.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(entry -> {
                        // YAY!!!!!!!!!!!!!!!!!!!
                        sortedValues.put(entry.getKey(), entry.getValue());
                    });

            tickIntervals = 0;
        }

        for(UUID uuid : values.keySet()) {

            if(!bossBars.containsKey(uuid)) {
                bossBars.put(uuid, Bukkit.createBossBar("", type.getBarColor(), BarStyle.SOLID));

                BossBar bossBar = bossBars.get(uuid);

                bossBar.addPlayer(Bukkit.getPlayer(uuid));
                bossBar.setVisible(true);
            }

            BossBar bossBar = bossBars.get(uuid);
            int position = new ArrayList<UUID>(values.keySet()).indexOf(uuid);
            ChatColor positionColor;

            switch(position) {
                case 1:
                    positionColor = ChatColor.YELLOW;
                    break;
                case 2:
                    positionColor = ChatColor.GRAY;
                    break;
                case 3:
                    positionColor = ChatColor.GOLD;
                    break;
                default:
                    positionColor = ChatColor.AQUA;
                    break;
            }

            bossBar.setTitle(type.getBarText()
                    .replaceAll("%position%", position + "")
                    .replaceAll("%time_left%", TimeUtil.toTimeFormat(type.getContestLength() - (System.currentTimeMillis() - contestStart)))
            );
            bossBar.setProgress((System.currentTimeMillis() - contestStart) / type.getContestLength());

        }

        tickIntervals++;
    }

    public void end() {
        //Give rewards
    }

    public UUID getUUID() {
        return uuid;
    }

    public ContestType getType() {
        return type;
    }

    public String getName() {
        return type.getName();
    }

    public String[] getDescription() {
        return type.getDescription();
    }

    public long getContestLength() {
        return type.getContestLength();
    }

    public ContestAction getAction() {
        return type.getAction();
    }

    public Map<Material, Integer> getContestApplicableMaterials() {
        return type.getContestApplicableMaterials();
    }

    public boolean hasBossBar() {
        return getBarColor() != null && getBarText() != null;
    }

    public String getBarText() {
        return type.getBarText();
    }

    public BarColor getBarColor() {
        return type.getBarColor();
    }

    public long getAutoRenew() {
        return type.getAutoRenew();
    }

    public Map<UUID, Integer> getValues() {
        return values;
    }

    public long getContestStart() {
        return contestStart;
    }

    public boolean isContestOver() {
        return System.currentTimeMillis() >= contestStart + getContestLength();
    }

}
