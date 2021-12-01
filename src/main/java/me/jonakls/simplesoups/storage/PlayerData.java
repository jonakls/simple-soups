package me.jonakls.simplesoups.storage;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.storage.cache.DataCache;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {

    private final Map<UUID, DataCache> playerData = new HashMap<>();
    private final DataStorage dataStorage;

    public PlayerData(PluginCore pluginCore) {
        this.dataStorage = pluginCore.getStorage();
    }

    public void loadPlayerData(Player player) {
        DataCache dataCache = new DataCache();
        UUID uuid = player.getUniqueId();
        this.playerData.put(uuid, dataCache);
        dataCache.setUuid(uuid.toString());
        dataCache.setPlayerName(player.getName());
        dataCache.setKills(dataStorage.getKills(uuid.toString()));
        dataCache.setDeaths(dataStorage.getDeaths(uuid.toString()));
        dataCache.setXp(dataStorage.getXp(uuid.toString()));
        dataCache.setKdr(dataStorage.getKDR(uuid.toString()));
        dataCache.setKits(dataStorage.getKits(uuid.toString()));
    }

    public void savePlayerData(Player player) {
        DataCache dataCache = this.playerData.get(player.getUniqueId());
        dataStorage.insert(dataCache);
    }

    public void incrementKills(Player player) {
        int current = this.playerData.get(player.getUniqueId()).getKills();
        this.playerData.get(player.getUniqueId()).setKills(current + 1);
    }

    public void incrementDeaths(Player player) {
        int current = this.playerData.get(player.getUniqueId()).getDeaths();
        this.playerData.get(player.getUniqueId()).setDeaths(current + 1);
    }

    public void incrementXp(Player player) {
        int current = this.playerData.get(player.getUniqueId()).getXp();
        this.playerData.get(player.getUniqueId()).setXp(current + 1);
    }

    public void updateKDR(Player player) {
        int kills = this.playerData.get(player.getUniqueId()).getKills();
        int deaths = this.playerData.get(player.getUniqueId()).getDeaths();
        if (deaths == 0) {
            this.playerData.get(player.getUniqueId()).setKdr((float) kills);
            return;
        }
        this.playerData.get(player.getUniqueId()).setKdr((float) kills / deaths);
    }

    public DataCache getPlayerData(Player player) {
        return playerData.get(player.getUniqueId());
    }
}
