package com.wiggle1000.hideandseek;

import com.wiggle1000.hideandseek.cmds.cmdHideAndSeek;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class HideAndSeek extends JavaPlugin {

    public static Logger LOGGER = Logger.getLogger("HideAndSeek");

    public static HideAndSeekInstance hideAndSeekInstance;

    public static List<HideAndSeekMap> maps = new ArrayList<>();

    public static Scoreboard scoreboard;
    public static Team hiders;
    public static Team seekers;
    public static Objective timeRemainingObjective;


    @Override
    public void onEnable() {
        // Plugin startup logic
        LOGGER.log(Level.INFO, "HideAndSeek Enabling.");
        ConfigurationSerialization.registerClass(HideAndSeekMap.class);
        saveDefaultConfig();
        //getServer().getCommandMap().register("aa",)
        this.getCommand("hideandseek").setExecutor(new cmdHideAndSeek());
        hideAndSeekInstance = new HideAndSeekInstance();
        loadMaps();
        SetupTeams();
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
        {
            @Override
            public void run()
            {
                hideAndSeekInstance.tick(1f/20f);
            }
        }, 0, 1);
        LOGGER.log(Level.INFO, "HideAndSeek Enabled.");

    }

    public void SetupTeams()
    {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        timeRemainingObjective = scoreboard.registerNewObjective("timer", Criteria.create("time"), Component.text("Time Remaining"), RenderType.INTEGER);
        timeRemainingObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        hiders = scoreboard.registerNewTeam("hiders");
        hiders.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
        hiders.displayName(Component.text("Hiders"));
        hiders.color(NamedTextColor.BLUE);
        hiders.suffix(Component.text(" [Hiding]"));

        seekers = scoreboard.registerNewTeam("seekers");
        seekers.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        seekers.displayName(Component.text("Seekers"));
        seekers.color(NamedTextColor.RED);
        seekers.suffix(Component.text(" [Seeking]"));
    }

    public void loadMaps()
    {
        maps = (List<HideAndSeekMap>) getConfig().get("maps", new ArrayList<HideAndSeekMap>());
    }

    public void saveMaps()
    {
        getConfig().set("maps", maps);
        saveConfig();
    }

    @Override
    public void onDisable() {
        saveMaps();
        hiders.unregister();
        seekers.unregister();
        saveConfig();
        // Plugin shutdown logic
    }
}
