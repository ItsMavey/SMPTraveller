package SMPTraveller;

import SMPTraveller.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import SMPTraveller.utils.DataManager;
import SMPTraveller.utils.Teleports;

import java.util.Objects;

public final class SMPTraveller extends JavaPlugin {

    Teleports teleports = null;

    @Override
    public void onEnable() {
        // Plugin startup logic

        Bukkit.getLogger().info("SMPTraveller by ItsMavey on Twitch");
        Bukkit.getLogger().info("Starting up...");

        this.teleports = new Teleports(this);

        // Register commands

        Objects.requireNonNull(getCommand("spawn")).setExecutor(new Spawn(teleports));
        Objects.requireNonNull(getCommand("shop")).setExecutor(new Shop(teleports));
        Objects.requireNonNull(getCommand("end")).setExecutor(new End(teleports));
        Objects.requireNonNull(getCommand("travel")).setExecutor(new Travel(teleports));
        Objects.requireNonNull(getCommand("traveller")).setExecutor(new Traveller(teleports));

        // Load data

        DataManager.setup();
        DataManager.load(teleports);

        // Register events

        // Register listeners

        // Register tasks

        getServer().getScheduler().runTaskTimer(this, () -> DataManager.save(teleports), 6000L, 6000L);


        Bukkit.getLogger().info("SMPTraveller is ready to go!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        Bukkit.getLogger().info("Shutting down...");

        Bukkit.getLogger().info("Saving data...");
        DataManager.save(this.teleports);

        Bukkit.getLogger().info("SMPTraveller has been disabled.");
    }
}
