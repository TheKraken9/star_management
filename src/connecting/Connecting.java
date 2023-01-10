package connecting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The type Connecting.
 */
public class Connecting {
    private String username;
    private String password;
    private String port;
    private String database;

    private Connecting(String username, String password, String port) {
        this.username = username;
        this.password = password;
        this.port = port;
    }

    private Connection getOracleConnection() throws SQLException {
        Connection co = null;
        co = DriverManager.getConnection("jdbc:oracle:thin:@localhost:"+port+":orcl",username, password);
        return co;
    }

    /**
     * Connect to oracle connection.
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    public static Connection connectToOracle() throws SQLException {
        Connection co;
        Connecting con = new Connecting("scott","tiger","1521");
        co = con.getOracleConnection();
        return co;
    }
}
