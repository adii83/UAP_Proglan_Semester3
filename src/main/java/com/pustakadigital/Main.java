package com.pustakadigital;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Menjalankan aplikasi di thread Swing (Event Dispatch Thread)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Membuat dan menampilkan jendela login
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);  // Menampilkan jendela login
            }
        });
    }
}
