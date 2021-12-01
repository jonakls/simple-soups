package me.jonakls.simplesoups.listener;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.SimpleSoups;
import me.jonakls.simplesoups.enums.GameStatus;
import me.jonakls.simplesoups.handlers.DeathMessagesHandler;
import me.jonakls.simplesoups.handlers.KillStreakHandler;
import me.jonakls.simplesoups.manager.FileManager;
import me.jonakls.simplesoups.manager.KillStreakManager;
import me.jonakls.simplesoups.storage.PlayerData;
import me.jonakls.simplesoups.utils.CountdownTimer;
import me.jonakls.simplesoups.utils.ItemFactory;
import me.jonakls.simplesoups.utils.PlayerUtils;
import me.jonakls.simplesoups.utils.TitleFactory;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerDeathListener implements Listener {

    private final SimpleSoups plugin;
    private final PlayerData playerCache;
    private final FileManager lang;
    private final FileManager config;
    private final KillStreakHandler killStreakHandler;
    private final KillStreakManager killStreak;
    private final DeathMessagesHandler deathMessages;

    public PlayerDeathListener(PluginCore pluginCore) {
        this.plugin = pluginCore.getPlugin();
        this.playerCache = pluginCore.getPlayerData();
        this.lang = pluginCore.getFilesLoader().getLang();
        this.config = pluginCore.getFilesLoader().getConfig();
        this.killStreak = pluginCore.getManagers().getKillStreakManager();
        this.killStreakHandler = pluginCore.getHandlersLoader().getKillStreakHandler();
        this.deathMessages = pluginCore.getHandlersLoader().getDeathMessagesHandler();
    }

    @EventHandler
    public void updateStats(PlayerDeathEvent event) {

        Player player = event.getEntity();
        Player killer = player.getKiller();

        if (killer != null) {

            playerCache.incrementKills(killer);
            playerCache.incrementDeaths(player);
            playerCache.incrementXp(killer);
            playerCache.updateKDR(player);
            playerCache.updateKDR(killer);
            killStreak.add(killer);
            killStreak.reset(player);
            killStreakHandler.actions(killer);
        }
    }

    @EventHandler
    public void deathMessages(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        if (killer == null) {
            deathMessages.objectsDamage(player);
            return;
        }
        deathMessages.playersDamage(player, killer);

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event) {
        event.getEntity().setMetadata(
                "status", new FixedMetadataValue(plugin, GameStatus.SPAWN)
        );
        event.getEntity().spigot().respawn();
        event.setKeepInventory(true);
        PlayerUtils.clearInventory(event.getEntity());
        event.setNewLevel(0);
        event.setDroppedExp(0);

        String prefix = lang.getString("prefix");

        ItemFactory builder = new ItemFactory(
                config.getMaterial("items-join.kits.material"),
                1,
                config.getString("items-join.kits.display"),
                config.getStringList("items-join.kits.lore")
        );

        event.getEntity().getInventory().setItem(
                config.getInt("items-join.kits.slot"),
                builder.getItem()
        );

        TitleFactory title = new TitleFactory(
                lang.getString("titles.player-death.title"),
                lang.getString("titles.player-death.sub-title")
        );

        title.setTime(
                lang.getInt("titles.player-death.fade-in"),
                lang.getInt("titles.player-death.stay"),
                lang.getInt("titles.player-death.fade-out")
        );

        CountdownTimer timer = new CountdownTimer(
                plugin,
                config.getInt("respawn-time"),
                () -> {
                    title.send(event.getEntity());
                    event.getEntity().setGameMode(GameMode.SPECTATOR);
                    if (!(config.contains("spawn.world"))) {
                        event.getEntity().sendMessage(prefix + lang.getString("error.no-spawn"));
                        return;
                    }

                    event.getEntity().teleport(new Location(
                            Bukkit.getWorld(config.getString("spawn.world")),
                            config.getDouble("spawn.x"),
                            config.getDouble("spawn.y"),
                            config.getDouble("spawn.z"),
                            (float) config.getDouble("spawn.yaw"),
                            (float) config.getDouble("spawn.pitch")
                    ));
                },
                () -> {
                    event.getEntity().setGameMode(GameMode.SURVIVAL);
                    if (!(config.contains("spawn.world"))) {
                        event.getEntity().sendMessage(prefix + lang.getString("error.no-spawn"));
                        return;
                    }

                    event.getEntity().teleport(new Location(
                            Bukkit.getWorld(config.getString("spawn.world")),
                            config.getDouble("spawn.x"),
                            config.getDouble("spawn.y"),
                            config.getDouble("spawn.z"),
                            (float) config.getDouble("spawn.yaw"),
                            (float) config.getDouble("spawn.pitch")
                    ));

                },
                (t) -> {
                    event.getEntity().sendMessage(prefix + lang.getString("messages.death-countdown")
                            .replace("%seconds%", "" + t.getSecondsLeft()));

                    if (config.getBoolean("sounds.death-countdown.enable")) {
                        event.getEntity().playSound(
                                event.getEntity().getLocation(),
                                Sound.valueOf(config.getString("sounds.death-countdown.sound")),
                                (float) config.getDouble("sounds.death-countdown.vol"),
                                (float) config.getDouble("sounds.death-countdown.pitch")
                        );
                    }
                }
        );
        timer.scheduleTimer();
    }
}
