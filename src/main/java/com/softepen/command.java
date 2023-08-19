package com.softepen;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;

import static com.softepen.SGuard.*;
import static com.softepen.Staff.Join.stopCountdown;
import static com.softepen.Staff.PlayerIP.getPlayerIP;
import static com.softepen.Staff.staffList.isStaff;
import static com.softepen.Staff.verifyCode.verifyCode;

public class command implements Listener {
    private final Plugin plugin;
    public command(Plugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) throws SQLException {
        Player player = event.getPlayer();
        String command = event.getMessage().split(" ")[0].substring(1); // Komutu al

        if (isStaff(player)) {
            if (command.equalsIgnoreCase("sguard")) {
                if (!(player instanceof Player)) {
                    player.sendMessage(prefix + " " + noConsole);
                    return;
                }
                String[] args = event.getMessage().split(" "); // Komut argümanlarını al
                if (args.length != 2) {
                    player.sendMessage(prefix + " " + confirm);
                    return;
                }
                String verificationCode = args[1];
                String code = null;
                if (verifyCode(player, verificationCode)) {
                    String insertQuery = "INSERT INTO History (username, ip_address, code) VALUES (?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setString(1, player.getName());
                    preparedStatement.setString(2, getPlayerIP(player));
                    preparedStatement.setString(3, verificationCode);
                    preparedStatement.executeUpdate();

                    player.sendMessage(prefix + " " + confirmed);
                    stopCountdown(player);
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "effect clear " + player.getName());

                    String insertQueryWithoutCode = "INSERT INTO ipList (username, ip_address) VALUES (?, ?)";
                    PreparedStatement preparedStatementWithoutCode = connection.prepareStatement(insertQueryWithoutCode);
                    preparedStatementWithoutCode.setString(1, player.getName());
                    preparedStatementWithoutCode.setString(2, getPlayerIP(player));
                    preparedStatementWithoutCode.executeUpdate();

                } else {
                    player.sendMessage(prefix + " " + confirmMessage);
                }
            } else if (command.equalsIgnoreCase("login")) {
            } else if (command.equalsIgnoreCase("register")) {
            } else {
            }
        } else if (verificationKeys.containsKey(player)) {
            event.setCancelled(true);
        } else {
            if (player.hasPermission("softepen.staff")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.kickPlayer(banMessage);
                        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ipban " + player.getName() + " " + banMessage);
                    }
                }.runTask(plugin);
            }
        }
    }
}