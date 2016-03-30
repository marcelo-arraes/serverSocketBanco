package core;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco implements Serializable {

    private static final long serialVersionUID = -6692995550062772027L;

    private static final String DRIVER_CONNECTION = "org.postgresql.Driver";
    private static final String URL_CONNECTION = "jdbc:postgresql://localhost:5432/banco";
    private static final String USER_CONNECTION = "postgres";
    private static final String PASSWORD_CONNECTION = "root";

    private static Connection conn = null;

    public static Connection getConnection() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if (conn == null) {
            Class.forName(DRIVER_CONNECTION).newInstance();

            conn = DriverManager.getConnection(URL_CONNECTION,
                    USER_CONNECTION, PASSWORD_CONNECTION);
        }

        return conn;
    }

    public static void close() throws SQLException {
            if (conn != null) {
                conn.close();
            }
    }
}
