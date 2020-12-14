package jdbc;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.sql.Date;
import java.sql.*;
import java.time.ZoneId;
import java.util.*;

public class TablePopulator {

    public void populateUsers(int usersCount) {
        Faker faker = new Faker();
        String insertUserQuery = "INSERT INTO users (name,surname,birthdate) VALUES (?,?,?);";
        for (int i = 0; i < usersCount; i++) {
            try (Connection connection = DBConnector.getInstance().getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery)
            ) {
                Date birthDay = Date.valueOf(faker.date().birthday(18, 75).toInstant().atZone(ZoneId.of("UTC")).toLocalDate());
                preparedStatement.setString(1, faker.name().firstName());
                preparedStatement.setString(2, faker.name().lastName());
                preparedStatement.setDate(3, birthDay);
                preparedStatement.executeUpdate();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void populateFriendship(int friendshipCount) {
        java.util.Date currentDay = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDay);
        calendar.add(Calendar.YEAR, 5);
        java.util.Date futureDate = calendar.getTime();

        Faker faker = new Faker();
        String selectUsersQuery = "SELECT id FROM users";
        ArrayList<Integer> ids = getIds(selectUsersQuery);
        List<Pair<Integer, Integer>> uniquePairs = generateUniquePairs(ids, ids, friendshipCount);
        String insertFriendshipQuery = "INSERT INTO friendships (userid1,userid2,timestamp) VALUES (?,?,?);";

        for (Pair<Integer, Integer> friendship : uniquePairs) {
            try (Connection connection = DBConnector.getInstance().getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertFriendshipQuery)
            ) {
                java.util.Date fakeTimestamp = faker.date().between(currentDay, futureDate);
                Timestamp timestamp = new Timestamp(fakeTimestamp.getTime());
                preparedStatement.setInt(1, friendship.getKey());
                preparedStatement.setInt(2, friendship.getValue());
                preparedStatement.setTimestamp(3, timestamp);
                preparedStatement.executeUpdate();

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void populatePosts(int postsCount) {
        java.util.Date currentDay = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDay);
        calendar.add(Calendar.YEAR, 5);
        java.util.Date futureDate = calendar.getTime();

        Faker faker = new Faker();
        String insertUserQuery = "INSERT INTO posts (userid,text,timestamp) VALUES (?,?,?);";

        String selectUsersQuery = "SELECT id FROM users";
        ArrayList<Integer> ids = getIds(selectUsersQuery);
        List<Pair<Integer, Integer>> uniquePairs = generateUniquePairs(ids, ids, postsCount);

        for (int i = 0; i < postsCount; i++) {
            try (Connection connection = DBConnector.getInstance().getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery)
            ) {
                java.util.Date fakeTimestamp = faker.date().between(currentDay, futureDate);
                Timestamp timestamp = new Timestamp(fakeTimestamp.getTime());
                preparedStatement.setInt(1, uniquePairs.get(i).getKey());
                preparedStatement.setString(2, generateRandomText());
                preparedStatement.setTimestamp(3, timestamp);
                preparedStatement.executeUpdate();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void populateLikes(int likesCount) {
        java.util.Date currentDay = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDay);
        calendar.add(Calendar.YEAR, 5);
        java.util.Date futureDate = calendar.getTime();

        Faker faker = new Faker();
        String selectPostsQuery = "SELECT id FROM posts";
        String insertUserQuery = "INSERT INTO likes (postid,userid,timestamp) VALUES (?,?,?);";

        ArrayList<Integer> ids = getIds(selectPostsQuery);
        List<Pair<Integer, Integer>> uniquePairs = generateUniquePairs(ids, ids, likesCount);

        for (int i = 0; i < likesCount; i++) {
            try (Connection connection = DBConnector.getInstance().getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery)
            ) {
                java.util.Date fakeTimestamp = faker.date().between(futureDate, futureDate);
                Timestamp timestamp = new Timestamp(fakeTimestamp.getTime());
                preparedStatement.setInt(1, uniquePairs.get(i).getKey());
                preparedStatement.setInt(2, uniquePairs.get(i).getValue());
                preparedStatement.setTimestamp(3, timestamp);
                preparedStatement.executeUpdate();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }

    }

    private List<Pair<Integer, Integer>> generateUniquePairs(ArrayList<Integer> firstArray, ArrayList<Integer> secondArray, int count) {
        Set<Pair<Integer, Integer>> friendship = new HashSet<>();
        Random randomFirstIndex = new Random();
        Random randomSecondIndex = new Random();
        for (int i = 0; i < count; i++) {
            friendship.add(Pair.of(firstArray.get(randomFirstIndex.nextInt(firstArray.size())), secondArray.get(randomSecondIndex.nextInt(secondArray.size()))));
        }
        return new ArrayList<>(friendship);
    }

    private ArrayList<Integer> getIds(String selectQuery) {
        ArrayList<Integer> ids = new ArrayList<>();

        try (Connection connection = DBConnector.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(selectQuery);
        ) {
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return ids;
    }

    private String generateRandomText() {
        final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        final String NUMBER = "0123456789";

        final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
        String randomText = "";

        Random random = new Random();
        int randomCount = random.nextInt(DATA_FOR_RANDOM_STRING.length());
        for (int i = 0; i < randomCount ; i++){
            randomText = randomText + DATA_FOR_RANDOM_STRING.charAt(randomCount);
        }
        return DATA_FOR_RANDOM_STRING;
    }

}
