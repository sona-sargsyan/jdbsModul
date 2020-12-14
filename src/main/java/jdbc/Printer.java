package jdbc;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Printer {

    private List<Pair<String, String>> selectUsers(int friendsCount, int likesCount) {
        List<Pair<String, String>> users = new ArrayList<>();
        String query = Constant.FINAL_PRINTING_QUERY;
        try (Connection connection = DBConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, friendsCount);
            preparedStatement.setInt(2, likesCount);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                users.add(Pair.of(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(users);
    }

    public void printUsers(int friendsCount, int likesCount) {
        List<Pair<String, String>> users = selectUsers(friendsCount, likesCount);
        System.out.println("Users Name which has more then " + likesCount + "likes. And more then " + friendsCount + " friends:");
        for (Pair<String, String> user : users) {
            System.out.println("User name and surname is: " + user.getKey() + " " + user.getValue());
        }

    }
}
