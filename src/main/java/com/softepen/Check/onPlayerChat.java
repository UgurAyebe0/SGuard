package com.softepen.Check;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static com.softepen.SGuard.*;
import static com.softepen.Staff.staffList.isStaff;

public class onPlayerChat implements Listener {

    private final Plugin plugin;

    public onPlayerChat(Plugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (isStaff(player)) {
            if (verificationKeys.containsKey(player)) {
                if (!event.isCancelled()) {
                    event.setCancelled(true);
                    player.sendMessage(prefix + " " + confirmMessage);
                }
                return;
            }
            if (isStaff(player)) {
                return;
            }

            if (player.hasPermission("Sguard-BAN")) {
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

}
