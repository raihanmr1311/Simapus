package modules;

import java.sql.Connection;
import java.sql.DriverManager;

import io.github.cdimascio.dotenv.Dotenv;

public class Database {

    private Dotenv dotenv = Dotenv.load();
    private String host = dotenv.get("DB_HOST");
    private String user = dotenv.get("DB_USER");
    private String password = dotenv.get("DB_PASSWORD");
    private Connection connection;

    private static volatile Database instance = null;

    private Database() {
        try {
            connection = DriverManager.getConnection(host, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized(Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }

        return instance;
    }


    public Connection getConnection() {
        return connection;
    }
}
