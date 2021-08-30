package me.gardendev.simplesoups.manager;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/*

The following class was created based on a plugin called "KillStreak" by the author AllanGame

Author: https://github.com/AllanGame
Repository: https://github.com/AllanGame/minecraft-KillStreak-plugin
Plugin Spigot: https://www.spigotmc.org/resources/kill-streak.91784/

*/

public class KillStreakManager {

    private final Map<String, Integer> streakList;

    public KillStreakManager() {
        this.streakList = new HashMap<String, Integer>(){};
    }

    public void set(Player player, Integer streak) {
        this.streakList.put(player.getUniqueId().toString(), streak);
    }

    public void add(Player player) {
        Integer currentStreak = this.streakList.get(player.getUniqueId().toString());
        if (currentStreak == null) {
            this.set(player, 0);
            currentStreak = 0;
        }
        player.setLevel(currentStreak + 1);
        this.streakList.put(player.getUniqueId().toString(), currentStreak + 1);
    }

    public void remove(Player player) {
        if (this.getStreak(player) > 0) {
            this.streakList.put(player.getUniqueId().toString(), this.getStreak(player) - 1);
        }
    }

    public void reset(Player player) {
        this.set(player, 0);
    }

    public Integer getStreak(Player player) {
        Integer currentStreak = this.streakList.get(player.getUniqueId().toString());
        if(currentStreak == null) {
            this.set(player, 0);
            currentStreak = 0;
        }
        return currentStreak;
    }

    public Map<String, Integer> getStreakList() {
        return streakList;
    }
}
