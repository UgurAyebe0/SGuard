package com.softepen.Staff;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;

import static com.softepen.Mysql.deleteVerificationKey.deleteVerificationKey;
import static com.softepen.SGuard.verificationKeys;
import static com.softepen.Staff.staffList.isStaff;

public class Settings implements Listener {

    private final Plugin plugin;

    public Settings(Plugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (isStaff(player)) {
            if (verificationKeys.containsKey(player)) {
                verificationKeys.remove(player);
                deleteVerificationKey(player);
            }
        }
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (isStaff(player)) {

            if (verificationKeys.containsKey(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if (isStaff(player)) {

            if (verificationKeys.containsKey(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (isStaff(player)) {
            if (verificationKeys.containsKey(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (isStaff(player)) {
            if (verificationKeys.containsKey(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (isStaff(player)) {

            if (verificationKeys.containsKey(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (isStaff(player)) {

            if (verificationKeys.containsKey(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMouseMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (isStaff(player)) {
            if (verificationKeys.containsKey(player)) {
                event.getPlayer().setSneaking(true);
            }
        }
    }
}

