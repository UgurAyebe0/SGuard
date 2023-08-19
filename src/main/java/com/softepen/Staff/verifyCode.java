package com.softepen.Staff;

import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.softepen.SGuard.connection;
import static com.softepen.SGuard.verificationKeys;
public class verifyCode {
    public static boolean verifyCode(Player player, String code) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM SCode WHERE username='" + player.getName() + "' AND code='" + code + "'");

            if (resultSet.next()) {
                statement.executeUpdate("DELETE FROM SCode WHERE username='" + player.getName() + "'");
                verificationKeys.remove(player);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
