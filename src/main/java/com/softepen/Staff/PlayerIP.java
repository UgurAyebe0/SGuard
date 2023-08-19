package com.softepen.Staff;

import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.softepen.SGuard.*;


public class PlayerIP {

    public static String getPlayerIP(Player player) {
        return player.getAddress().getAddress().getHostAddress();
    }

    public static boolean hasMatchingIP(String playerIP, String username) {
        System.out.println(playerIP);
        System.out.println(username);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM ipList WHERE ip_address = ? AND username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, playerIP);
            preparedStatement.setString(2, username);
            resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet.next());
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
