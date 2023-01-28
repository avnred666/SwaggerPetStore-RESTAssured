package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {
    private Properties properties;
    private final String propertyPath = "src/test/resources/config.properties";

    public ConfigProperties() throws IOException {
        properties = new Properties();
        FileInputStream fis = new FileInputStream(propertyPath);
        properties.load(fis);
    }

    public String getBaseURI(){
        return properties.getProperty("BASE_URI");
    }

}
