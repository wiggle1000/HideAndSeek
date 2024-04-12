package com.wiggle1000.hideandseek;

import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.logging.Level;

public class HideAndSeekInstance
{
    public ArrayList<Player> playingPlayers = new ArrayList<>();
    public ArrayList<Player> seekingPlayers = new ArrayList<>();
    public GameState cState = GameState.STOPPED;
    public float secondsInState = 0;

    public HideAndSeekMap cMap;

    public HideAndSeekInstance()
    {

    }
    public void ForceStartGame()
    {
        ChangeState(GameState.HIDING);
    }

    public void ChooseMap()
    {
        if(HideAndSeek.maps.size() == 0)
        {
            HideAndSeek.LOGGER.log(Level.WARNING, "Hide and seek has no maps! Stopping game..");
            ChangeState(GameState.STOPPED);
        }
        cMap = HideAndSeek.maps.get(RandomUtils.nextInt(0, HideAndSeek.maps.size()));
    }

    public void AddPlayer(Player player)
    {
        if(playingPlayers.contains(player))
        {
            player.sendMessage("[Hide and Seek] Already Playing! Use '/hideandseek quit' to leave");
            return;
        }
        player.setScoreboard(HideAndSeek.scoreboard);
        playingPlayers.add(player);
        HideAndSeek.hiders.addPlayer(player);
        AnnounceToPlayers(Component.text("Player ").append(player.name()).append(Component.text(" has joined!")));
    }
    public void RemovePlayer(Player player)
    {

        if(!playingPlayers.contains(player))
        {
            player.sendMessage("[Hide and Seek] Not in game! Use '/hideandseek join' to play");
            return;
        }
        HideAndSeek.hiders.removePlayer(player);
        HideAndSeek.seekers.removePlayer(player);
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        AnnounceToPlayers(Component.text("Player ").append(player.name()).append(Component.text(" has quit.")));
        playingPlayers.remove(player);
    }

    public void AnnounceToPlayers(Component component)
    {
        playingPlayers.forEach(player -> player.sendMessage(Component.text("[Hide and Seek] ").append(component)));
    }

    public void tick(float dt)
    {
        secondsInState += dt;
        for(Player p : playingPlayers)
        {
            if(!p.isOnline())
            {
                AnnounceToPlayers(Component.text("Player "+p.name()+" has quit."));
                playingPlayers.remove(p);
            }
        }

        HideAndSeek.timeRemainingObjective.setAutoUpdateDisplay(true);
        HideAndSeek.timeRemainingObjective.displayName(Component.text(GameStateToString(cState)));
        HideAndSeek.timeRemainingObjective.getScore("").setScore((int)secondsInState);
    }

    public void ChangeState(GameState newState)
    {
        cState = newState;
        secondsInState = 0;

    }

    public String GameStateToString(GameState state)
    {
        return switch (state)
        {
            case STOPPED -> "Stopped";
            case AWAITING_PLAYERS -> "Awaiting Players";
            case LOBBY -> "Lobby";
            case HIDING -> "Hiding";
            case PLAYING -> "Seeking";
            case ROUND_END ->"Round End";
        };
    }

    public enum GameState
    {
        STOPPED,
        AWAITING_PLAYERS,
        LOBBY,
        HIDING,
        PLAYING,
        ROUND_END
    }
}
