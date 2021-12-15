package modules;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Model {

    private static Connection con = Database.getInstance().getConnection();
    
    public static PreparedStatement prepare(String sql) throws SQLException {
        return con.prepareStatement(sql);     
    }
}
