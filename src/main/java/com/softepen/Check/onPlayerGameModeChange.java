package com.softepen.Check;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static com.softepen.SGuard.banMessage;
import static com.softepen.Staff.staffList.isStaff;

public class onPlayerGameModeChange implements Listener {
    private final Plugin plugin;

    public onPlayerGameModeChange(Plugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        GameMode newGameMode = event.getNewGameMode();

        if (newGameMode == GameMode.CREATIVE && !isStaff(player)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.kickPlayer(banMessage);
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ipban " + player.getName() + " " + banMessage);
                }
            }.runTask((Plugin) this);
        }
        if (newGameMode == GameMode.SPECTATOR && !isStaff(player)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.kickPlayer(banMessage);
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ipban " + player.getName() + " " + banMessage);
                }
            }.runTask((Plugin) this);
        }
    }
}
