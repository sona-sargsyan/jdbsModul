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
}
