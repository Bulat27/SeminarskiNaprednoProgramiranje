package rs.ac.bg.fon.nprog.server.properties.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Dragon
 */
public class UtilApplicationProperties {

    private final Properties properties;
    private static UtilApplicationProperties instance;

    private static final String APPLICATION_PROPERTIES_FILE_PATH = "Server/src/main/resources/application.properties";
    private static final String APPLICATION_PROPERTIES_DB_URL = "db.url";
    private static final String APPLICATION_PROPERTIES_DB_USERNAME = "db.username";
    private static final String APPLICATION_PROPERTIES_DB_PASSWORD = "db.password";
    private static final String APPLICATION_PROPERTIES_SERVER_PORT = "server.port";

    private UtilApplicationProperties() throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream(new File(APPLICATION_PROPERTIES_FILE_PATH)));
    }

    public static UtilApplicationProperties getInstance() throws IOException {
        if (instance == null) {
            instance = new UtilApplicationProperties();
        }
        return instance;
    }

    public String getDatabaseURL() {
        return properties.getProperty(APPLICATION_PROPERTIES_DB_URL);
    }

    public String getDatabaseUsername() {
        return properties.getProperty(APPLICATION_PROPERTIES_DB_USERNAME);
    }

    public String getDatabasePassword() {
        return properties.getProperty(APPLICATION_PROPERTIES_DB_PASSWORD);
    }

    public int getServerPort() {
        return Integer.parseInt(properties.getProperty(APPLICATION_PROPERTIES_SERVER_PORT));
    }

    public void setDatabaseURL(String value) {
        properties.setProperty(APPLICATION_PROPERTIES_DB_URL, value);
    }

    public void setDatabaseUsername(String value) {
        properties.setProperty(APPLICATION_PROPERTIES_DB_USERNAME, value);
    }

    public void setDatabasePassword(String value) {
        properties.setProperty(APPLICATION_PROPERTIES_DB_PASSWORD, value);
    }

    public void setServerPort(String value) {
        properties.setProperty(APPLICATION_PROPERTIES_SERVER_PORT, value);
    }

    public void saveChanges() throws IOException {
        properties.store(new FileOutputStream(new File(APPLICATION_PROPERTIES_FILE_PATH)), null);
    }
}
