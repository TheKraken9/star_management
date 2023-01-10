import connecting.Connecting;
import utilities.Composition;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws SQLException the sql exception
     */
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