package org.qe.Base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
    public Properties properties;
    public int RESPONSE_200 = 200;
    public void base() throws IOException {
        properties = new Properties();
        FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/org/qe/Config/config.properties");
        properties.load(file);
    }
}
