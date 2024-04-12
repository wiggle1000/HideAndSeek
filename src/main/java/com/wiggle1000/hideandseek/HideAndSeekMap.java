package com.wiggle1000.hideandseek;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HideAndSeekMap implements ConfigurationSerializable
{
    public String mapName = "default_map_name";
    public String title = "Default Map Title";
    public String authorName = "AUTHORNAME";
    public Location[] spawns;
    public float hideTime = 10;

    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("name", this.mapName);
        data.put("title", this.title);
        data.put("author", this.authorName);
        data.put("hideTime", this.hideTime);
        data.put("spawns", this.spawns);

        return data;
    }

    public static HideAndSeekMap deserialize(Map<String, Object> args) {
        HideAndSeekMap map = new HideAndSeekMap();
        map.mapName = (String) args.getOrDefault("name", "default_map_name");
        map.title = (String) args.getOrDefault("title", "Default Map Title");
        map.authorName = (String) args.getOrDefault("author", "Default Author");
        map.hideTime = (float) args.getOrDefault("hideTime", 10);
        map.spawns = (Location[]) args.get("spawns");
        return map;
    }
}
