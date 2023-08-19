package com.softepen.Staff;

import org.bukkit.entity.Player;

import static com.softepen.SGuard.staffName;

public class staffList {
    public static boolean isStaff(Player player) {
        for (String staffName : staffName) {
            if (player.getName().equalsIgnoreCase(staffName)) {
                return true;
            }
        }
        return false;
    }


}
