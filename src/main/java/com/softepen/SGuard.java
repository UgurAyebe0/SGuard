package com.softepen;

import com.softepen.Check.checkInventory;
import com.softepen.Check.onPlayerChat;
import com.softepen.Check.onPlayerGameModeChange;
import com.softepen.Check.onPlayerMove;
import com.softepen.Staff.Join;
import com.softepen.Staff.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.softepen.Mysql.createTableIfNotExists.createTableIfNotExists;

public final class SGuard extends JavaPlugin implements Listener {
    public static FileConfiguration config;
    public static Map<Player, String> verificationKeys;
    public static String host, database, username, password, prefix,unconfirmed,banMessage,sublin1,sublin2,pleaseaccount,autoaccount,verfyaccount,noConsole,
            confirm,noPerm,confirmMessage,confirmed,forbiddenItems,forbiddenEnchant,ErrorItems;
    public static boolean autologin;
    public static int port, kicktime;
    public static Connection connection;
    public static String[] staffName;

    @Override
    public void onEnable() {

        loadConfig();
        reloadConfig(); // config dosyasını belleğe yükle
        saveConfig();   // değişiklikleri kaydet
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            createTableIfNotExists();
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        verificationKeys = new HashMap<>();
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new Join(this), this);
        getServer().getPluginManager().registerEvents(new Settings(this), this);
        getServer().getPluginManager().registerEvents(new command(this), this);
        getServer().getPluginManager().registerEvents(new checkInventory(this), this);
        getServer().getPluginManager().registerEvents(new onPlayerChat(this), this);
        getServer().getPluginManager().registerEvents(new onPlayerGameModeChange(this), this);
        getServer().getPluginManager().registerEvents(new onPlayerMove(this), this);
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("&c-=-=-=-=-=-=-= &6Softepen &c-=-=-=-=-=-=-=");
        getLogger().info("&aPlugin Successfully Enabled.");
        getLogger().info("&aFor support, visit www.Softepen.com");
        getLogger().info("&c-=-=-=-=-=-=-= &6S-Guard &c-=-=-=-=-=-=-=");

    }
    void loadConfig() {

        reloadConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        config = getConfig();
        host = getConfig().getString("mysql.host");
        port = getConfig().getInt("mysql.port");
        database = getConfig().getString("mysql.database");
        username = getConfig().getString("mysql.username");
        password = getConfig().getString("mysql.password");
        autologin = getConfig().getBoolean("settings.autologin");
        staffName = getConfig().getStringList("staff").toArray(new String[0]);
        confirmed = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.confirmed"));
        confirm = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.confirm"));
        confirmMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.confirm-message"));
        kicktime = getConfig().getInt("settings.kicktime");
        noConsole = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.no-console"));
        prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.prefix"));
        unconfirmed = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.unconfirmed"));
        banMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.ban-message"));
        sublin1 = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.sublin1"));
        sublin2 = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.sublin2"));
        pleaseaccount = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.pleaseaccount"));
        autoaccount = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.autoaccount"));
        verfyaccount = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.verfyaccount"));
        noPerm = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.noPerm"));
        forbiddenItems = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.forbiddenItems"));
        forbiddenEnchant = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.forbiddenEnchant"));
        ErrorItems = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.Errorİtems"));
        reloadConfig(); // config dosyasını belleğe yükle
        saveConfig();   // değişiklikleri kaydet
    }
    @Override
    public void onDisable() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                getLogger().info("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getLogger().info("&c-=-=-=-=-=-=-= &6Softepen &c-=-=-=-=-=-=-=");
        getLogger().info("&4An error occurred in the plugin");
        getLogger().info("&4For support, visit www.Softepen.com");
        getLogger().info("&c-=-=-=-=-=-=-= &6Softepen &c-=-=-=-=-=-=-=");
    }


}
