package com.example.sbt;

import android.os.StrictMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
    private static final String URL = "jdbc:mysql://192.168.42.49:8080/schooldb";
    private static final String USERNAME = "roots";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() {
        Connection connection = null;

        // Enable the strict mode for network operations (do not run on the main thread in production)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
