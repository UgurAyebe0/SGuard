package com.softepen.Mysql;

import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.sql.Statement;

import static com.softepen.SGuard.connection;

public class deleteVerificationKey {
    public static void deleteVerificationKey(Player player) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM " + "SCode" + " WHERE username='" + player.getName() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
