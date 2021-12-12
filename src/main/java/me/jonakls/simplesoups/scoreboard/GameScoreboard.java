package me.jonakls.simplesoups.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.SimpleSoups;
import me.jonakls.simplesoups.manager.FileManager;
import me.jonakls.simplesoups.storage.PlayerData;
import me.jonakls.simplesoups.storage.cache.DataCache;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GameScoreboard {

    private final FileManager lang;
    private final FileManager config;
    private final PlayerData playerData;
    private final SimpleSoups plugin;

    private final Map<UUID, FastBoard> boards = new HashMap<>();

    public GameScoreboard(PluginCore pluginCore) {
        this.playerData = pluginCore.getPlayerData();
        this.lang = pluginCore.getFilesLoader().getLang();
        this.config = pluginCore.getFilesLoader().getConfig();
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
                .replace("%player_kdr%", "" + cache.getKdr())
        );
        fastBoard.updateLines(lines);
    }

    public void runTaskUpdate() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            for (FastBoard board : getBoards().values()) {
                this.update(board);
            }
        }, 0, config.getLong("scoreboard.update-ticks"));
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
