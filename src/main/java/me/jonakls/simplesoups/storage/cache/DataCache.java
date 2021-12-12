package me.jonakls.simplesoups.storage.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class DataCache {

    private final List<String> kits = new ArrayList<>();
    private String playerName;
    private String uuid;
    private int kills;
    private int deaths;
    private float kdr;
    private int xp;

    public void addKit(String kit) {
        this.kits.add(kit);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public float getKdr() {
        return Math.round(kdr);
    }

    public void setKdr(float kdr) {
        this.kdr = kdr;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public String getStringKits() {
        StringJoiner joiner = new StringJoiner(",");
        for (String kit : kits) {
            joiner.add(kit);
        }
        return joiner.toString();
    }

    public List<String> getKits() {
        return kits;
    }

    public void setKits(String... kits) {
        this.kits.addAll(Arrays.asList(kits));
    }
}
