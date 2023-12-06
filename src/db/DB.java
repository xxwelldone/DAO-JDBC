package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
    private static Connection conn = null;

    public static Connection getCon() {
        if (conn == null) {
            try {
                Properties props = loadProperty();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            } catch (Exception e) {
                throw new DBException(e.getMessage());
            }

        }
        return conn;
    }

    public static void closeCon() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
    }

    private static Properties loadProperty() {
        try (FileInputStream fs = new FileInputStream("src/db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new DBException(e.getMessage());
        }

    }
}
