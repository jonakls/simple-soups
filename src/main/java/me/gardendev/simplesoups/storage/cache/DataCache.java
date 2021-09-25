package me.gardendev.simplesoups.storage.cache;

import java.util.List;

public class DataCache {

    private String playerName;
    private String uuid;
    private int kills;
    private int deaths;
    private float kdr;
    private int xp;
    private List<String> kits;

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setKdr(float kdr) {
        this.kdr = kdr;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    /*
    public void setKits(String...kits) {
        this.kits.removeAll(Arrays.stream(kits).toList());
    }
    */

    public String getPlayerName() {
        return playerName;
    }

    public String getUuid() {
        return uuid;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public float getKdr() {
        return kdr;
    }

    public int getXp() {
        return xp;
    }

    public List<String> getKits() {
        return kits;
    }
}
