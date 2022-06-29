package rs.ac.bg.fon.nprog.server.repository.database.connection;



import rs.ac.bg.fon.nprog.server.properties.util.UtilApplicationProperties;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 */
public class DBConnectionFactory {

    private Connection connection;
    private static DBConnectionFactory instance;

    private DBConnectionFactory() {
    }

    public static DBConnectionFactory getInstance() {
        if (instance == null) {
            instance = new DBConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException, IOException {
        if (connection == null || connection.isClosed()) {
            try {
                String url = UtilApplicationProperties.getInstance().getDatabaseURL();
                String user = UtilApplicationProperties.getInstance().getDatabaseUsername();
                String password = UtilApplicationProperties.getInstance().getDatabasePassword();

                connection = DriverManager.getConnection(url, user, password);
                connection.setAutoCommit(false);
            } catch (SQLException ex) {
                System.out.println("Error while establishing the database connection: " + ex.getMessage());
                throw ex;
            }
        }
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public void commitTransaction() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.commit();
        }
    }

    public void rollbackTransaction() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.rollback();
        }
    }
}
