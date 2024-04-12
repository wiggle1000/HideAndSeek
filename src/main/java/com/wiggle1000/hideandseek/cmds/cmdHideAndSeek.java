package com.wiggle1000.hideandseek.cmds;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class cmdHideAndSeek implements CommandExecutor
{
    public void ShowSingleCommandHelp(Player player, String command, String use)
    {
        player.sendMessage(
                Component.text("Command: ").color(TextColor.color(0.4f,0.8f,0.8f))
                .append(Component.text(command).color(TextColor.color(1f,1f,1f)))
                .appendNewline()
                .append(Component.text("  "+use).color(TextColor.color(0.8f,0.8f,0.8f)))
        );
    }
    public void ShowHelp(Player player)
    {
        player.sendMessage(Component.text("Hide and Seek help").color(TextColor.color(0.4f,0.8f,0.8f)).decorate(TextDecoration.BOLD));
        ShowSingleCommandHelp(player, "/hideandseek join", "Joins the hide and seek lobby.");
        ShowSingleCommandHelp(player, "/hideandseek quit", "Leave the hide and seek lobby.");
        ShowSingleCommandHelp(player, "/hideandseek reset", "Resets hide and seek state");
        ShowSingleCommandHelp(player, "/hideandseek map create <name>", "Starts add-a-map mode");
        ShowSingleCommandHelp(player, "/hideandseek map addSpawn", "Adds a spawn to map");
        ShowSingleCommandHelp(player, "/hideandseek map clearSpawns", "Clears spawns on map");
        ShowSingleCommandHelp(player, "/hideandseek map setAuthor <name>", "sets current map's author");
        ShowSingleCommandHelp(player, "/hideandseek map setHideTime <seconds>", "sets current map's hide time before seeker can move");

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if(sender instanceof Player player)
        {
            if(args.length == 1)
            {
                String subCommand = args[0].toLowerCase();
                if(subCommand.equals("join"))
                {
                    if (!player.hasPermission("hideandseek.join"))
                    {
                        player.sendMessage(Component.text("You don't have permission to join Hide and Seek!").color(TextColor.color(1f,0f,0f)));
                        ShowHelp(player);
                        return true;
                    }
                    Commands.Join(player);
                    return true;
                }
                else if(subCommand.equals("quit"))
                {
                    if (!player.hasPermission("hideandseek.join"))
                    {
                        player.sendMessage(Component.text("No permission.").color(TextColor.color(1f,0f,0f)));
                        ShowHelp(player);
                        return true;
                    }
                    Commands.Quit(player);
                    return true;
                }
            }
            else //show help
            {
                ShowHelp(player);
                return true;
            }
        }
        return false;
    }
}
