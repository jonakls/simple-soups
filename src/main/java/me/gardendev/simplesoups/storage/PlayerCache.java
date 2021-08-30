package me.gardendev.simplesoups.storage;

import me.gardendev.simplesoups.PluginCore;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerCache {

    private final DataStorage storage;

    private final List<Player> players;
    private final Map<String, String> id;
    private final Map<String, Integer> kills;
    private final Map<String, Integer> deaths;
    private final Map<String, Integer> xp;
    private final Map<String, List<String>> kits;

    public PlayerCache(PluginCore pluginCore) {
        this.storage = pluginCore.getStorage();
        this.players = new ArrayList<>();
        this.id = new HashMap<>();
        this.kills = new HashMap<>();
        this.deaths = new HashMap<>();
        this.xp = new HashMap<>();
        this.kits = new HashMap<>();
    }

    public void forceSaveData() {
        for (Player player : players) {
            this.storage.insert(this, player);
            players.remove(player);
            kills.remove(player.getName());
        }
    }

    public void savePlayerData(Player player) {
        this.storage.insert(this, player);
        this.players.remove(player);
        this.kills.remove(player.getName());

    }

    public void loadPlayerData(Player player) {
        players.add(player);
        this.kills.put(player.getName(), storage.getKills(player.getUniqueId().toString()));
        this.deaths.put(player.getName(), storage.getDeaths(player.getUniqueId().toString()));
        this.xp.put(player.getName(), storage.getXp(player.getUniqueId().toString()));
    }

    public void registerPlayer(Player player) {
        if (players.contains(player)) return;
        players.add(player);
        id.put(player.getName(), player.getUniqueId().toString());
    }

    public void incrementKills(Player player) {
        Integer killsValue = this.kills.get(player.getName());
        if(killsValue == null) {
            this.kills.put(player.getName(), 0);
            killsValue = 0;
        }
        this.kills.put(player.getName(), killsValue + 1 );
    }

    public void incrementDeaths(Player player) {
        Integer deathsValue = this.deaths.get(player.getName());
        if(deathsValue == null) {
            this.deaths.put(player.getName(), 0);
            deathsValue = 0;
        }
        this.deaths.put(player.getName(), deathsValue + 1 );
    }

    public void incrementXp(Player player) {
        Integer xpValue = this.xp.get(player.getName());
        if(xpValue == null) {
            this.xp.put(player.getName(), 0);
            xpValue = 0;
        }
        this.xp.put(player.getName(), xpValue + 1 );
    }

    public int getKills(Player player) {
        Integer killsValue = this.kills.get(player.getName());
        if(killsValue == null) {
            this.kills.put(player.getName(), 0);
            killsValue = 0;
        }
        return killsValue;
    }

    public int getDeaths(Player player) {
        Integer deathsValue = this.deaths.get(player.getName());
        if(deathsValue == null) {
            this.deaths.put(player.getName(), 0);
            deathsValue = 0;
        }
        return deathsValue;
    }

    public int getXp(Player player) {
        Integer xpValue = this.xp.get(player.getName());
        if(xpValue == null) {
            this.xp.put(player.getName(), 0);
            xpValue = 0;
        }
        return xpValue;
    }

    public String getId(Player player) {
        String idValue = this.id.get(player.getName());
        if (idValue == null) {
            this.id.put(player.getName(), player.getName());
            idValue = player.getName();
        }
        return idValue;
    }

}
