package com.softepen.Mysql;

import org.bukkit.entity.Player;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.softepen.SGuard.*;

public class saveVerificationKey {

    public static void saveVerificationKey(Player player, String verificationKey) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO " + "SCode" + " (username, code) VALUES ('" + player.getName() + "', '" + verificationKey + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
