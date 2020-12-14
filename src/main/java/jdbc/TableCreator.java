package jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreator {
    public void createTable(String query) {
        try (Connection connection = DBConnector.getInstance().getConnection();
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(query);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
