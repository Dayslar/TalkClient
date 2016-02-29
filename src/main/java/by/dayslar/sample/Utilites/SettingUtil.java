package by.dayslar.sample.Utilites;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class SettingUtil {

    private static Logger LogArea = Logger.getLogger(SettingUtil.class);
    private static Properties properties;

    private static final String SETTING_PROPERTIES_NAME = "setting.properties";
    private static final String DIRECTORY_PROPERTY_NAME = "directory";

    public static void loadSetting(){
        properties = new Properties();
        try {
            File file = new File(SETTING_PROPERTIES_NAME);
            properties.load(new FileInputStream(file));

        } catch (IOException e) {
            LogArea.error("Не удалось загрузить настройки: " + e.getMessage());

        }
    }

    public static void saveSetting(String directory){
        properties.put(DIRECTORY_PROPERTY_NAME, directory);

        try {
            File file = new File(SETTING_PROPERTIES_NAME);
            OutputStream outputStream = new FileOutputStream(file);
            properties.store(outputStream, null);

        } catch (IOException e) {
            e.printStackTrace();
            LogArea.error("Не удалось сохранить настройки: " + e.getMessage());

        }

    }

    public static String getDirectory(){
        return properties.getProperty(DIRECTORY_PROPERTY_NAME);
    }


}
