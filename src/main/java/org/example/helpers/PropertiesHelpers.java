package org.example.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelpers {

    private static Properties properties;
    private static String linkFile;
    private static FileInputStream file;

    // 1. Khởi tạo và Load file config ngay khi gọi class
    public static Properties loadAllFiles() {
        properties = new Properties();
        try {
            // Lấy đường dẫn file config
            linkFile = System.getProperty("user.dir") + "/src/test/resources/config.properties";

            // Đọc file
            file = new FileInputStream(linkFile);

            // Load nội dung vào biến properties
            properties.load(file);
            file.close();

            return properties;
        } catch (Exception e) {
            e.printStackTrace();
            return new Properties();
        }
    }

    // 2. Hàm lấy giá trị theo Key
    public static String getValue(String key) {
        // Nếu properties chưa được load thì load lại cho chắc
        if (properties == null) {
            properties = loadAllFiles();
        }

        // Lấy giá trị, nếu không có thì trả về null
        try {
            return properties.getProperty(key);
        } catch (Exception e) {
            System.out.println("Không tìm thấy giá trị cho key: " + key);
            return null;
        }
    }
}