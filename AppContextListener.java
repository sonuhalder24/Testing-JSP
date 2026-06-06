package com;

import java.io.File;
import java.io.FileOutputStream;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        String[] paths = {
            "/projects/challenge/testapp.txt",
            System.getProperty("user.dir") + "/testapp.txt",
            System.getProperty("user.home") + "/testapp.txt"
        };
        byte[] content = "testDataDummy".getBytes();
        for (String path : paths) {
            FileOutputStream fos = null;
            try {
                File f = new File(path);
                fos = new FileOutputStream(f);
                fos.write(content);
                fos.flush();
            } catch (Exception e) {
                // try next path
            } finally {
                if (fos != null) try { fos.close(); } catch (Exception e) {}
            }
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {}
}
