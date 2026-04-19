package utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	private static Properties properties;
   // private static Properties prop = new Properties();

    static {
        try {
            String env = System.getProperty("env"); // qa, qa maurya, preprod, prod

            if (env == null) {
                env = "qa"; 
            }


            System.out.println("Running on ENV: " + env);

            String fileName = "config-" + env + ".properties";

            InputStream is = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream(fileName);

            if (is == null) {
                throw new RuntimeException("Config file not found: " + fileName);
            }

            properties = new Properties();
            properties.load(is);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config file", e);
        }
    }
    public static String getProperty(String key) {
        return properties.getProperty(key);
        
    }
}