package com.pustakadigital.controller;

import com.pustakadigital.view.LoginFrame;
import org.apache.log4j.Logger;

import javax.swing.*;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        System.out.println("Classpath: " + System.getProperty("java.class.path"));


        logger.debug("Logging initialized successfully.");


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });
    }
}
