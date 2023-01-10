import connecting.Connecting;
import utilities.Composition;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;


public class Main {
    public static void main(String[] args) throws SQLException {

        Connection co = Connecting.connectToOracle();
        Composition composition = new Composition();
        try {
            composition.getAllSubCompositions(co);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}