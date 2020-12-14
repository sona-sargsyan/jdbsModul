package jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Printer {

    public static void printUsers(int likesCount, int friendsCount) {
        String query = "? ? ";
        try (Connection connection = DBConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, likesCount);
            preparedStatement.setInt(2, friendsCount);
            preparedStatement.executeQuery();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
