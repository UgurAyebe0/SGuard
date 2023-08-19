package com.softepen.Check;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.sql.rowset.spi.SyncFactoryException;

import static com.softepen.SGuard.*;
import static com.softepen.Staff.staffList.isStaff;
import static javax.sql.rowset.spi.SyncFactory.getLogger;

public class checkInventory implements Listener {

    private final Plugin plugin;

    public checkInventory(Plugin plugin) {
        this.plugin = plugin;
    }
    public static void CInventory(Player player){
        if (!isStaff(player)) {
            for (String itemName : config.getConfigurationSection("Forbidden.items").getKeys(false)) {
                Material material = Material.getMaterial(itemName);
                if (material == null) {
                    try {
                        getLogger().warning(ErrorItems + " " + itemName);
                    } catch (SyncFactoryException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                }

                int maxAmount = config.getInt("Forbidden.items." + itemName);

                int count = 0;
                for (ItemStack item : player.getInventory().getContents()) {
                    if (item != null && item.getType() == material) {
                        count += item.getAmount();
                    }
                }

                if (count > maxAmount) {
                    player.kickPlayer(forbiddenItems);
                    break;
                }
            }
            ConfigurationSection enchantSection = config.getConfigurationSection("Forbidden.enchant");
            for (String enchantName : enchantSection.getKeys(false)) {
                int maxLevel = enchantSection.getInt(enchantName);

                for (ItemStack item : player.getInventory().getContents()) {
                    if (item != null && item.getEnchantments().containsKey(Enchantment.getByKey(NamespacedKey.minecraft(enchantName)))) {
                        int currentLevel = item.getEnchantments().get(Enchantment.getByKey(NamespacedKey.minecraft(enchantName)));

                        if (currentLevel > maxLevel) {
                            player.kickPlayer(forbiddenEnchant);
                            return;
                        }
                    }
                }
            }
        }
    }


}
