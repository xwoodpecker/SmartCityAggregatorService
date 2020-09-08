package htw.smartcity.aggregator.base;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * The type Config properties.
 */
public class ConfigProperties {

    private ConfigProperties() { }

    /**
     * The constant BROKER.
     */
    public static final String BROKER;
    /**
     * The constant USERNAME.
     */
    public static final String USERNAME;
    /**
     * The constant PASSWORD.
     */
    public static final String PASSWORD;
    /**
     * The constant CERTIFICATE.
     */
    public static final String CERTIFICATE;
    /**
     * The constant TOPIC.
     */
    public static final String TOPIC;
    /**
     * The constant INITIAL_ADMIN_PASSWORD.
     */
    public static final String INITIAL_ADMIN_PASSWORD;
    /**
     * The constant MAIL_USERNAME.
     */
    public static final String MAIL_USERNAME;
    /**
     * The constant MAIL_PASSWORD.
     */
    public static final String MAIL_PASSWORD;
    /**
     * The constant MAIL_LIST.
     */
    public static final String MAIL_LIST;
    /**
     * The constant MAIL_SEND_PERIOD.
     */
    public static final String MAIL_SEND_PERIOD;
    /**
     * The constant MAIL_HOST
     */
    public static final String MAIL_HOST;
    /**
     * The constant MAIL_PORT
     */
    public static final String MAIL_PORT;
    /**
     * The constant MAIL_SENDER
     */
    public static final String MAIL_SENDER;

    static {
        URL root = ConfigProperties.class.getProtectionDomain().getCodeSource().getLocation();
        URL propertiesFile = null;
        try {
            propertiesFile = new URL(root, "application.properties");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Properties properties = new Properties();
        try {
            properties.load(propertiesFile.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        BROKER = properties.getProperty("BROKER");
        USERNAME = properties.getProperty("USERNAME");
        PASSWORD = properties.getProperty("PASSWORD");
        CERTIFICATE = properties.getProperty("CERTIFICATE");
        TOPIC = properties.getProperty("TOPIC");
        MAIL_USERNAME = properties.getProperty("MAIL_USERNAME");
        MAIL_PASSWORD = properties.getProperty("MAIL_PASSWORD");
        MAIL_LIST = properties.getProperty("MAIL_LIST");
        MAIL_SEND_PERIOD = properties.getProperty("MAIL_SEND_PERIOD");
        INITIAL_ADMIN_PASSWORD = properties.getProperty("INITIAL_ADMIN_PASSWORD");
        MAIL_HOST = properties.getProperty("MAIL_HOST");
        MAIL_PORT = properties.getProperty("MAIL_PORT");
        MAIL_SENDER= properties.getProperty("MAIL_SENDER");
    }
}
