package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The type Config properties.
 */
public class ConfigProperties {

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

    static {

        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String configPath = rootPath + "config.properties";


        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(configPath));
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
