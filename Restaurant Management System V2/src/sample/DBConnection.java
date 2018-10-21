package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String dbName="restaurantdb";
        String username="root";
        String password="toor";

        Connection connection= DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);

        return connection;
    }
}
