package jdbc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {

    public static DBConnector dbConnector;
    Properties properties;

    private DBConnector() throws IOException {
        properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.properties");
        properties.load(inputStream);

        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getDbUrl() {
        return String.format("jdbc:postgresql://%s:%s/%s",
                properties.getProperty("db.host"),
                properties.getProperty("db.port"),
                properties.getProperty("db.name"));
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                getDbUrl(),
                properties.getProperty("db.username"),
                properties.getProperty("db.password"));
    }

    public static DBConnector getInstance() throws SQLException, IOException {
        if (dbConnector == null) {
            dbConnector = new DBConnector();
        } else if (dbConnector.getConnection().isClosed()) {
            dbConnector = new DBConnector();
        }
        return dbConnector;
    }
}
