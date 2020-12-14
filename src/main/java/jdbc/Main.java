package jdbc;

public class Main {

    public static void main(String[] args) {
        TableCreator tableCreator = new TableCreator();
        tableCreator.createTable(Constant.CREATE_USER_QUERY);
        tableCreator.createTable(Constant.CREATE_FRIENDSHIP_QUERY);
        tableCreator.createTable(Constant.CREATE_POST_QUERY);
        tableCreator.createTable(Constant.CREATE_LIKE_QUERY);
        
        TablePopulator tablePopulator = new TablePopulator();
        tablePopulator.populateUsers(900);
        tablePopulator.populateFriendship(70000);
        tablePopulator.populatePosts(100000);
        tablePopulator.populateLikes(300000);

        Printer printer= new Printer();
        printer.printUsers(80,100);
    }
}
