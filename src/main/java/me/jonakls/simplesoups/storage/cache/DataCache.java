package me.jonakls.simplesoups.storage.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class DataCache {

    private String playerName;
    private String uuid;
    private int kills;
    private int deaths;
    private float kdr;
    private int xp;
    private final List<String> kits = new ArrayList<>();

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

    public void setKits(String... kits) {
        this.kits.addAll(Arrays.asList(kits));
    }

    public void addKit(String kit) {
        this.kits.add(kit);
    }

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
}
