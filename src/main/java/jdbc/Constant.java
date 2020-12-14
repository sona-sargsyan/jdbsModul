package jdbc;

public class Constant {
    public static final String CREATE_USER_QUERY = "CREATE TABLE users (" +
            "id serial PRIMARY KEY," +
            "name VARCHAR ( 50 ) NOT NULL," +
            "surname VARCHAR ( 50 ) NOT NULL," +
            "birthdate Date NOT NULL);";

    public static final String CREATE_POST_QUERY = "CREATE TABLE posts (" +
            "id serial PRIMARY KEY," +
            "userId int NOT NULL," +
            "text text NULL," +
            "timestamp TIMESTAMP NOT NULL," +
            "    FOREIGN KEY(userid)" +
            "REFERENCES users(id));";

    public static final String CREATE_FRIENDSHIP_QUERY = "CREATE TABLE friendships (" +
            "userid1 INT," +
            "userid2 INT," +
            "timestamp TIMESTAMP NOT NULL," +
            "    FOREIGN KEY(userid1) " +
            "REFERENCES users(id)," +
            "    FOREIGN KEY(userid2)" +
            "REFERENCES users(id)," +
            "PRIMARY KEY(userid1,userid2));";

    public static final String CREATE_LIKE_QUERY = "CREATE TABLE likes (" +
            "postid INT," +
            "userid INT," +
            "timestamp TIMESTAMP NOT NULL," +
            "    FOREIGN KEY(postid) " +
            "REFERENCES posts(id)," +
            "    FOREIGN KEY(userid)" +
            "REFERENCES users(id)," +
            "PRIMARY KEY(postid,userid));";


    public static final String FINAL_PRINTING_QUERY ="SELECT users.NAME, \n" +
            "       users.surname \n" +
            "FROM   (SELECT * \n" +
            "        FROM   (SELECT * \n" +
            "                FROM   (SELECT f.userid1, \n" +
            "                               Count(*) AS friends_count \n" +
            "                        FROM   friendships f \n" +
            "                        GROUP  BY f.userid1) AS friends \n" +
            "                WHERE  friends_count > ?) AS reach_users \n" +
            "               JOIN (SELECT * \n" +
            "                     FROM   (SELECT l.userid, \n" +
            "                                    l.timestamp, \n" +
            "                                    Count(*) AS likes_count \n" +
            "                             FROM   likes l \n" +
            "                             GROUP  BY l.userid, \n" +
            "                                       l.timestamp) AS like_o \n" +
            "                     WHERE  likes_count > ? \n" +
            "                            AND like_o.timestamp >= '2025-12-14 20:47:32.974') \n" +
            "                    AS \n" +
            "                    like_users \n" +
            "                 ON reach_users.userid1 = like_users.userid) AS filtered \n" +
            "       JOIN users \n" +
            "         ON users.id = filtered.userid ";
}
