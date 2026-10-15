package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

    static Properties prop;

    static {
        try {
            FileInputStream file = new FileInputStream("src/test/java/utils/config.properties");
            prop = new Properties();
            prop.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key){
        return prop.getProperty(key);
    }
}