package com.softepen.Mysql;

import java.sql.SQLException;
import java.sql.Statement;
import static com.softepen.SGuard.connection;

public class createTableIfNotExists {
    public static void createTableIfNotExists() throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS SCode (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(255) NOT NULL," +
                    "code VARCHAR(255) NOT NULL" +
                    ");");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ipList (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(36) NOT NULL," +
                    "ip_address VARCHAR(45) NOT NULL" +
                    ");");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS History (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(36) NOT NULL," +
                    "ip_address VARCHAR(255) NOT NULL," +
                    "code VARCHAR(255) NOT NULL," +
                    "date DATETIME DEFAULT CURRENT_TIMESTAMP" +
                    ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
