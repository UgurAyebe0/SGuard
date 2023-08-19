package com.softepen.Check;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.sql.rowset.spi.SyncFactoryException;

import static com.softepen.Check.checkInventory.CInventory;
import static com.softepen.SGuard.banMessage;
import static com.softepen.SGuard.verificationKeys;
import static com.softepen.Staff.staffList.isStaff;

public class onPlayerMove implements Listener {
    private final Plugin plugin;

    public onPlayerMove(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){

        Player player = event.getPlayer();
        CInventory(player);

        if (verificationKeys.containsKey(player)) {
            event.setCancelled(true);
            return;
        }


        if (player.hasPermission("SGuard-Perm") && !isStaff(player)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.kickPlayer(banMessage);
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ipban " + player.getName() + " " + banMessage);
                }
            }.runTask((Plugin) this);
        }
        if (!isStaff(player)) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                player.kickPlayer(banMessage);
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ipban " + player.getName() + " " + banMessage);
            }
        }
        if (!isStaff(player)) {
            if (player.getGameMode() == GameMode.SPECTATOR) {
                player.kickPlayer(banMessage);
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ipban " + player.getName() + " " + banMessage);
            }
        }
    }

}
