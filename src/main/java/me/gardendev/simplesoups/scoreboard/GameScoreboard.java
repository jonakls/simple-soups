package me.gardendev.simplesoups.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import me.gardendev.simplesoups.PluginCore;
import me.gardendev.simplesoups.SimpleSoups;
import me.gardendev.simplesoups.manager.FileManager;
import me.gardendev.simplesoups.storage.PlayerData;
import me.gardendev.simplesoups.storage.cache.DataCache;
import org.bukkit.entity.Player;

import java.util.*;

public class GameScoreboard {

    private final FileManager lang;
    private final PlayerData playerData;
    private final SimpleSoups plugin;

    private final Map<UUID, FastBoard> boards = new HashMap<>();

    public GameScoreboard(PluginCore pluginCore) {
        this.playerData = pluginCore.getPlayerData();
        this.lang = pluginCore.getFilesLoader().getLang();
        this.plugin = pluginCore.getPlugin();
    }

    public void create(Player player) {
        FastBoard board = new FastBoard(player);
        this.update(board);
        this.boards.put(player.getUniqueId(), board);

    }

    public void update(FastBoard fastBoard) {

        Player player = fastBoard.getPlayer();
        DataCache cache = this.playerData.getPlayerData(player);

        fastBoard.updateTitle(lang.getString("scoreboard.title"));
        List<String> lines = lang.getStringList("scoreboard.lines");
        lines.replaceAll(line -> line
                .replace("%player%", player.getName())
                .replace("%player_xp%", "" + cache.getXp())
                .replace("%player_kills%", "" + cache.getKills())
                .replace("%player_deaths%", "" + cache.getDeaths())
        );
        fastBoard.updateLines(lines);
    }

    public void runTaskUpdate() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            for (FastBoard board : getBoards().values()) {
                this.update(board);
            }
        },0,20L);
    }

    public void delete(Player player) {
        FastBoard board = this.boards.get(player.getUniqueId());

        if (board != null) {
            board.delete();
        }
        this.boards.remove(player.getUniqueId());
    }

    public Map<UUID, FastBoard> getBoards() {
        return boards;
    }
}
