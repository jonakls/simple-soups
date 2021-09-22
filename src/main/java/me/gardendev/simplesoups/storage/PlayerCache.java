package me.gardendev.simplesoups.storage;

import me.gardendev.simplesoups.PluginCore;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerCache {

    private final DataStorage storage;

    private final List<Player> players;
    private final Map<UUID, UUID> id;
    private final Map<UUID, Integer> kills;
    private final Map<UUID, Integer> deaths;
    private final Map<UUID, Integer> xp;
    private final Map<UUID, List<String>> kits;

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
            this.id.remove(player.getUniqueId());
            this.kills.remove(player.getUniqueId());
            this.deaths.remove(player.getUniqueId());
            this.xp.remove(player.getUniqueId());
        }
    }

    public void savePlayerData(Player player) {
        this.storage.insert(this, player);
        this.players.remove(player);
        this.id.remove(player.getUniqueId());
        this.kills.remove(player.getUniqueId());
        this.deaths.remove(player.getUniqueId());
        this.xp.remove(player.getUniqueId());
    }

    public void loadPlayerData(Player player) {
        players.add(player);
        this.kills.put(player.getUniqueId(), storage.getKills(player.getUniqueId().toString()));
        this.deaths.put(player.getUniqueId(), storage.getDeaths(player.getUniqueId().toString()));
        this.xp.put(player.getUniqueId(), storage.getXp(player.getUniqueId().toString()));
    }

    public void registerPlayer(Player player) {
        if (players.contains(player)) return;
        players.add(player);
        id.put(player.getUniqueId(), player.getUniqueId());
    }

    public void incrementKills(Player player) {
        Integer killsValue = this.kills.get(player.getUniqueId());
        if(killsValue == null) {
            this.kills.put(player.getUniqueId(), 0);
            killsValue = 0;
        }
        this.kills.put(player.getUniqueId(), killsValue + 1 );
    }

    public void incrementDeaths(Player player) {
        Integer deathsValue = this.deaths.get(player.getUniqueId());
        if(deathsValue == null) {
            this.deaths.put(player.getUniqueId(), 0);
            deathsValue = 0;
        }
        this.deaths.put(player.getUniqueId(), deathsValue + 1 );
    }

    public void incrementXp(Player player) {
        Integer xpValue = this.xp.get(player.getUniqueId());
        if(xpValue == null) {
            this.xp.put(player.getUniqueId(), 0);
            xpValue = 0;
        }
        this.xp.put(player.getUniqueId(), xpValue + 1 );
    }

    public int getKills(Player player) {
        Integer killsValue = this.kills.get(player.getUniqueId());
        if(killsValue == null) {
            this.kills.put(player.getUniqueId(), 0);
            killsValue = 0;
        }
        return killsValue;
    }

    public int getDeaths(Player player) {
        Integer deathsValue = this.deaths.get(player.getUniqueId());
        if(deathsValue == null) {
            this.deaths.put(player.getUniqueId(), 0);
            deathsValue = 0;
        }
        return deathsValue;
    }

    public int getXp(Player player) {
        Integer xpValue = this.xp.get(player.getUniqueId());
        if(xpValue == null) {
            this.xp.put(player.getUniqueId(), 0);
            xpValue = 0;
        }
        return xpValue;
    }

    public UUID getId(Player player) {
        UUID idValue = this.id.get(player.getUniqueId());
        if (idValue == null) {
            this.id.put(player.getUniqueId(), player.getUniqueId());
            idValue = player.getUniqueId();
        }
        return idValue;
    }

}
