package com.softepen.Staff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.common.value.qual.StringVal;

import static com.softepen.Mysql.generateVerificationKey.generateVerificationKey;
import static com.softepen.Mysql.saveVerificationKey.saveVerificationKey;
import static com.softepen.SGuard.*;
import static com.softepen.Staff.PlayerIP.getPlayerIP;
import static com.softepen.Staff.staffList.isStaff;

public class Join implements Listener {
    private static Plugin plugin;

    public Join(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String playerIP = getPlayerIP(player);

        if (isStaff(player) && PlayerIP.hasMatchingIP(playerIP, player.getName()) && autologin){
            player.sendMessage(prefix + autoaccount);
            return;
        }











        if (isStaff(player)) {
            if (!verificationKeys.containsKey(player)) {
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "effect give " + player.getName() + " blindness " + kicktime);
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "effect give " + player.getName() + " slowness " + kicktime);
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "effect give " + player.getName() + " nausea " + kicktime);
                String verificationKey = generateVerificationKey();
                verificationKeys.put(player, verificationKey);
                saveVerificationKey(player, verificationKey);
                startCountdown(player);
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    if (verificationKeys.containsKey(player)) {
                        player.kickPlayer(prefix + unconfirmed);
                    }
                }, kicktime * 20L);
            }
            if (isStaff(player)) {
                player.sendMessage(prefix + verfyaccount);
                player.sendTitle("", "", 0, 1, 0);
            }
        } else if (player.hasPermission("SGuard-K")) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    player.kickPlayer(banMessage);
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ipban " + player.getName() + " " + banMessage);
                }
            }.runTask(plugin);
        }
    }
    private static BossBar bossBar;
    private static BukkitTask countdownTask;

    public static void startCountdown(Player player) {
        int countdownTicks = kicktime * 20;
        int maxLevel = kicktime;

        bossBar = Bukkit.createBossBar("Softepen Server Guard www.Softepen.Com", BarColor.RED, BarStyle.SOLID);
        bossBar.addPlayer(player);

        countdownTask = new BukkitRunnable() {
            int tickCount = 0;

            @Override
            public void run() {
                if (tickCount <= countdownTicks) {
                    double percentageRemaining = 1.0 - ((double) tickCount / countdownTicks);
                    int newLevel = (int) Math.max(maxLevel * percentageRemaining, 0);

                    player.setLevel(newLevel);

                    double xpProgress = (maxLevel - newLevel) / (double) maxLevel;
                    xpProgress = Math.max(0.0, Math.min(xpProgress, 1.0));

                    float xpBarProgress = (float) xpProgress;
                    player.setExp(xpBarProgress);

                    bossBar.setProgress(percentageRemaining);

                    int secondsRemaining = countdownTicks / 20 - tickCount / 20;
                    String titleMessage = ChatColor.GREEN + pleaseaccount;
                    String subTitleMessage = ChatColor.YELLOW + sublin1 + " " + (secondsRemaining - 1) + " " + sublin2;
                    player.sendTitle(titleMessage, subTitleMessage, 0, 40, 10);

                    tickCount++;
                } else {
                    player.setLevel(0);
                    player.setExp(0);
                    bossBar.removeAll();
                    player.sendTitle("", "", 0, 1, 0);

                    cancel(); // Countdown görevini durdur
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }
    public static void stopCountdown(Player player) {
        if (countdownTask != null && !countdownTask.isCancelled()) {
            countdownTask.cancel();
            player.setLevel(0);
            player.setExp(0);
            bossBar.removeAll();
            player.sendTitle("", "", 0, 1, 0);
        }
    }

}
