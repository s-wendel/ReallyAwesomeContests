package shwendel.reallyawesomecontests.contest;

import org.bukkit.Material;
import org.bukkit.boss.BarColor;

import java.util.Map;

public class ContestType {

    // A contest type configurable in config.yml
    private String name;
    private String[] description;
    private long contestLength; // Time in millis the length of it
    private long autoRenew; // Time in millis auto start interval
    private ContestAction action;
    private Map<Material, Integer> contestMaterials; // Key: A material able to trigger this contest, Value: An optional ageable
    private String barText;
    private BarColor barColor;

    public ContestType(String name, String[] description, long contestLength, long autoRenew, ContestAction action, Map<Material, Integer> contestMaterials, String barText, BarColor barColor) {
        this.name = name;
        this.description = description;
        this.contestLength = contestLength;
        this.autoRenew = autoRenew;
        this.action = action;
        this.contestMaterials = contestMaterials;
        this.barText = barText;
        this.barColor = barColor;
    }

    public String getName() {
        return name;
    }

    public String[] getDescription() {
        return description;
    }

    public long getContestLength() {
        return contestLength;
    }

    public long getAutoRenew() {
        return autoRenew;
    }

    public ContestAction getAction() {
        return action;
    }

    public Map<Material, Integer> getContestApplicableMaterials() {
        return contestMaterials;
    }

    public String getBarText() {
        return barText;
    }

    public BarColor getBarColor() {
        return barColor;
    }

}
