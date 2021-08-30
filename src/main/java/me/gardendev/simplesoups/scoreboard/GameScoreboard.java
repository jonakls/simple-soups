package me.gardendev.simplesoups.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import me.gardendev.simplesoups.PluginCore;
import me.gardendev.simplesoups.manager.FileManager;
import me.gardendev.simplesoups.storage.PlayerCache;
import org.bukkit.entity.Player;

import java.util.*;

public class GameScoreboard {

    private final FileManager lang;
    private final PlayerCache playerCache;

    private final Map<UUID, FastBoard> boards = new HashMap<>();

    public GameScoreboard(PluginCore pluginCore) {
        this.playerCache = pluginCore.getPlayerCache();
        this.lang = pluginCore.getFilesLoader().getLang();
    }

    public void create(Player player) {
        FastBoard board = new FastBoard(player);

        board.updateTitle(lang.getString("scoreboard.title"));

        List<String> lines = lang.getStringList("scoreboard.lines");

        lines.replaceAll(line -> line
                .replace("%player%", player.getName())
                .replace("%player_xp%", "" + this.playerCache.getXp(player))
                .replace("%player_kills%", "" + this.playerCache.getKills(player))
                .replace("%player_deaths%", "" + this.playerCache.getDeaths(player))
        );

        board.updateLines(lines);
        this.boards.put(player.getUniqueId(), board);

    }

    public void update(FastBoard fastBoard) {

        fastBoard.updateTitle(lang.getString("scoreboard.title"));

        List<String> lines = lang.getStringList("scoreboard.lines");

        lines.replaceAll(line -> line
                .replace("%player%", fastBoard.getPlayer().getName())
                .replace("%player_xp%", "" + this.playerCache.getXp(fastBoard.getPlayer()))
                .replace("%player_kills%", "" + this.playerCache.getKills(fastBoard.getPlayer()))
                .replace("%player_deaths%", "" + this.playerCache.getDeaths(fastBoard.getPlayer()))
        );
        fastBoard.updateLines(lines);
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
