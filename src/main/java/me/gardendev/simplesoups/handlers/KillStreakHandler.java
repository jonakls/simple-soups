package me.gardendev.simplesoups.handlers;

import me.gardendev.simplesoups.manager.KillStreakManager;
import me.gardendev.simplesoups.builders.TitleBuilder;
import me.gardendev.simplesoups.loader.FilesLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class KillStreakHandler {

    private final FilesLoader files;
    private final KillStreakManager manager;

    public KillStreakHandler(FilesLoader files, KillStreakManager manager) {
        this.files = files;
        this.manager = manager;
    }

    public void actions(Player player) {

        switch (manager.getStreak(player)){
            case 3:
                this.rewards(player, 3);
                this.title(player, 3);
                this.broadcast(player, 3);
                break;
            case 5:
                this.rewards(player, 5);
                this.title(player, 5);
                this.broadcast(player, 5);
                break;
            case 10:
                this.rewards(player, 10);
                this.title(player, 10);
                this.broadcast(player, 10);
                break;
            case 15:
                this.rewards(player, 15);
                this.title(player, 15);
                this.broadcast(player, 15);
                break;
            case 20:
                this.rewards(player, 20);
                this.title(player, 20);
                this.broadcast(player, 20);
                break;
            case 25:
                this.rewards(player, 25);
                this.title(player, 25);
                this.broadcast(player, 25);
                break;
            case 30:
                this.rewards(player, 30);
                this.title(player, 30);
                this.broadcast(player, 30);
                break;
            default:
                player.setLevel(manager.getStreak(player));
                break;
        }

    }

    private void rewards(Player player, int streak) {

        for (String command : files.getLang().getStringList("kill-streaks.kills-" + streak + ".rewards")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
        }
    }

    private void broadcast(Player player, int streak) {
        Bukkit.broadcastMessage(files.getLang().getString("kill-streaks.broadcast")
                .replace("%player%", player.getName())
                .replace("%streak%", "" + streak));
    }

    private void title(Player player, int streak) {
        TitleBuilder builder = new TitleBuilder(
                files.getLang().getString("kill-streaks.kills-" + streak + ".title"),
                files.getLang().getString("kill-streaks.kills-" + streak + ".subtitle")
        );
        builder.setTime(
                files.getLang().getInt("kill-streaks.fade-in"),
                files.getLang().getInt("kill-streaks.stay"),
                files.getLang().getInt("kill-streaks.fade-out")
        );
        builder.send(player);
    }
}
