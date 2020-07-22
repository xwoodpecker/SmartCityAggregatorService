package htw.smartcity.aggregator.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class ConfigProperties {

    public static final String BROKER;
    public static final String USERNAME;
    public static final String PASSWORD;
    public static final String CERTIFICATE;
    public static final String TOPIC;

    static {
        //todo wrong path when running tests, figure out better method to load this resource
        URL root = ConfigProperties.class.getProtectionDomain().getCodeSource().getLocation();
        URL propertiesFile = null;
        try {
            propertiesFile = new URL(root, "config.properties");
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
    }
}