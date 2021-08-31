package me.gardendev.simplesoups.listener;

import me.gardendev.simplesoups.SimpleSoups;
import me.gardendev.simplesoups.manager.FileManager;
import me.gardendev.simplesoups.PluginCore;
import me.gardendev.simplesoups.builders.ItemBuilder;
import me.gardendev.simplesoups.builders.TitleBuilder;
import me.gardendev.simplesoups.enums.GameStatus;
import me.gardendev.simplesoups.handlers.KillStreakHandler;
import me.gardendev.simplesoups.manager.KillStreakManager;
import me.gardendev.simplesoups.storage.PlayerCache;
import me.gardendev.simplesoups.utils.CountdownTimer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;
import java.util.Random;

public class PlayerDeathListener implements Listener {

    private final SimpleSoups plugin;
    private final PlayerCache playerCache;
    private final FileManager lang;
    private final FileManager config;
    private final KillStreakManager killStreak;
    private final KillStreakHandler killStreakHandler;

    public PlayerDeathListener(PluginCore pluginCore) {
        this.plugin = pluginCore.getPlugin();
        this.playerCache = pluginCore.getPlayerCache();
        this.lang = pluginCore.getFilesLoader().getLang();
        this.config = pluginCore.getFilesLoader().getConfig();
        this.killStreak = pluginCore.getManagers().getKillStreakManager();
        this.killStreakHandler = pluginCore.getHandlersLoader().getKillStreakHandler();
    }

    @EventHandler
    public void killStreak(PlayerDeathEvent event) {

        Player player = event.getEntity();
        Player killer = player.getKiller();

        if (killer != null) {

            playerCache.incrementKills(killer);
            playerCache.incrementDeaths(player);
            playerCache.incrementXp(killer);
            playerCache.registerPlayer(player);
            playerCache.registerPlayer(killer);

            killStreak.add(killer);
            killStreak.reset(player);
            killStreakHandler.actions(killer);
        }

    }

    @EventHandler
    public void deathMessages(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        String prefix = lang.getString("prefix");
        Random random = new Random();
        EntityDamageEvent.DamageCause damageCause = player.getLastDamageCause().getCause();
        if (killer == null) {
            if (damageCause.equals(EntityDamageEvent.DamageCause.FALL)) {
                Bukkit.broadcastMessage(prefix + lang.getString("kill-messages.death-by-fall-damage")
                        .replace("%player%", player.getName()));
                return;
            }
            if (damageCause.equals(EntityDamageEvent.DamageCause.LAVA)) {
                Bukkit.broadcastMessage(prefix + lang.getString("kill-messages.death-by-lava")
                        .replace("%player%", player.getName()));
                return;
            }
            if (damageCause.equals(EntityDamageEvent.DamageCause.FIRE) ||
                    damageCause.equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {

                Bukkit.broadcastMessage(prefix + lang.getString("kill-messages.death-by-fire")
                        .replace("%player%", player.getName()));
                return;
            }
            if (damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) ||
                    damageCause.equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) {

                Bukkit.broadcastMessage(prefix + lang.getString("kill-messages.death-by-explosion")
                        .replace("%player%", player.getName()));
                return;
            }
            Bukkit.broadcastMessage(prefix + lang.getString("kill-messages.death-by-unknown")
                    .replace("%player%", player.getName()));
            return;
        }
        List<String> killsMessages = lang.getStringList("kill-messages.with-players");
        Bukkit.broadcastMessage(prefix + killsMessages.get(random.nextInt(killsMessages.size()))
                .replace("%player%", player.getName())
                .replace("%killer%", killer.getName()));

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event) {
        event.getEntity().setMetadata(
                "status", new FixedMetadataValue(plugin, GameStatus.SPAWN)
        );
        event.getEntity().spigot().respawn();
        event.setKeepInventory(true);
        event.getEntity().getInventory().clear();
        event.getEntity().getInventory().setBoots(null);
        event.getEntity().getInventory().setLeggings(null);
        event.getEntity().getInventory().setChestplate(null);
        event.getEntity().getInventory().setHelmet(null);
        event.setNewLevel(0);
        event.setDroppedExp(0);

        String prefix = lang.getString("prefix");

        ItemBuilder builder = new ItemBuilder(
                Material.valueOf(config.getString("items-join.kits.material")),
                1,
                config.getString("items-join.kits.display"),
                config.getStringList("items-join.kits.lore")
        );

        event.getEntity().getInventory().setItem(
                config.getInt("items-join.kits.slot"),
                builder.getItem()
        );

        TitleBuilder title = new TitleBuilder(
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
