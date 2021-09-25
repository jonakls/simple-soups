package me.gardendev.simplesoups.manager;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 * The following class was created based on a plugin called "KillStreak" by the author AllanGame
 *
 * Author: https://github.com/AllanGame
 * Repository: https://github.com/AllanGame/minecraft-KillStreak-plugin
 * Plugin Spigot: https://www.spigotmc.org/resources/kill-streak.91784/
 */

public class KillStreakManager {

    private final Map<UUID, Integer> streakList;

    public KillStreakManager() {
        this.streakList = new HashMap<>();
    }

    public void set(Player player, int streak) {
        this.streakList.put(player.getUniqueId(), streak);
    }

    public void add(Player player) {
        Integer currentStreak = this.streakList.get(player.getUniqueId());
        if (currentStreak == null) {
            this.set(player, 0);
            currentStreak = 0;
        }
        this.streakList.put(player.getUniqueId(), currentStreak + 1);
        player.setLevel(currentStreak + 1);
    }

    public void reset(Player player) {
        this.set(player, 0);
    }

    public int getStreak(Player player) {
        int currentStreak = this.streakList.get(player.getUniqueId());
        if(currentStreak == 0) {
            this.set(player, 0);
        }
        return currentStreak;
    }

    public Map<UUID, Integer> getStreakList() {
        return streakList;
    }
}
