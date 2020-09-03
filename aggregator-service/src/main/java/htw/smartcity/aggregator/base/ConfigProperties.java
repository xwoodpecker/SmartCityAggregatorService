package htw.smartcity.aggregator.base;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class ConfigProperties {

    private ConfigProperties() { }

    public static final String BROKER;
    public static final String USERNAME;
    public static final String PASSWORD;
    public static final String CERTIFICATE;
    public static final String TOPIC;
    public static final String INITIAL_ADMIN_PASSWORD;
    public static final String MAIL_USERNAME;
    public static final String MAIL_PASSWORD;
    public static final String MAIL_LIST;
    public static final String MAIL_SEND_PERIOD;

    static {

        // is this still a todo ? @David
        //todo wrong path when running tests, figure out better method to load this resource
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
    }
}
