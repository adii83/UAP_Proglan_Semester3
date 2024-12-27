package com.pustakadigital.controller;

import com.pustakadigital.view.LoginFrame;
import org.apache.log4j.Logger;

import javax.swing.*;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        // Print classpath for debugging
        System.out.println("Classpath: " + System.getProperty("java.class.path"));

        // Log a message to verify logging configuration
        logger.debug("Logging initialized successfully.");

        // Run the application in the Swing Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create and display the login window
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });
    }
}
