package com.wiggle1000.hideandseek.cmds;

import com.wiggle1000.hideandseek.HideAndSeek;
import org.bukkit.entity.Player;

import java.nio.charset.StandardCharsets;

public class Commands
{
    public static void Join(Player player)
    {
        HideAndSeek.hideAndSeekInstance.AddPlayer(player);
        player.addResourcePack(HideAndSeek.ResourcePackUUID, HideAndSeek.ResourcepackURL, null, "Additional resources required for HideAndSeek!", true);
    }

    public static void Quit(Player player)
    {
        HideAndSeek.hideAndSeekInstance.RemovePlayer(player);
    }

    public static void Reset(Player player)
    {

    }

    public static void MapCreate(Player player, String name)
    {

    }

    public static void MapAddSpawn(Player player)
    {

    }
    public static void MapSetAuthor(Player player, String author)
    {

    }
    public static void MapSetHideTime(Player player, float hideSeconds)
    {

    }
    public static void MapClearSpawns(Player player)
    {

    }
}
