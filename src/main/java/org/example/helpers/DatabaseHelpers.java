package org.example.helpers;

import org.example.utils.LogUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseHelpers {
    //Connection
//    private static final String URL = "jdbc:mysql://localhost:3307/automation_test";
//    private static final String USER = "root";
//    private static final String PASS = "";
    private static final String URL = PropertiesHelpers.getValue("db.url");
    private static final String USER = PropertiesHelpers.getValue("db.user");
    private static final String PASS = PropertiesHelpers.getValue("db.password");

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            LogUtils.error("Error connect to DB" + e.getMessage());
            return null;
        }
    }

    //Get User data from DB
    public static String getPasswordForUser(String username) {
        String password = "";
        try {
            //Connect
            Connection conn = getConnection();

            //SQL command
            String query = "SELECT password from users WHERE username = '" + username + "'";
            Statement stmt = conn.createStatement();

            //Execute and get result
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                password = rs.getString("password");
                LogUtils.info("Password found in database: " + password);
            }
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    // Kiểm tra user có tồn tại trong DB không
    public static boolean isUserExist(String username) {
        Connection conn = getConnection();
        try {
            String query = "SELECT count(*) FROM users WHERE username = '" + username + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Nếu count > 0 nghĩa là có tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
