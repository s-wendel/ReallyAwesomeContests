package shwendel.reallyawesomecontests;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import shwendel.reallyawesomecontests.contest.ContestManager;

public final class ReallyAwesomeContests extends JavaPlugin implements Listener {

    private static ReallyAwesomeContests instance;

    // Started on August 15 2023 :-)
    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static ReallyAwesomeContests getInstance() {
        return instance;
    }

    @EventHandler
    public void test(BlockBreakEvent event) {
        ContestManager.getLatestContest().addProgress(event.getPlayer().getUniqueId(), 1);
    }

}
