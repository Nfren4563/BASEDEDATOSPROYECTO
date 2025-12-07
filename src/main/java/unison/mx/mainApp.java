package unison.mx;

import javax.swing.*;

public class mainApp {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            interfazlogin login = new interfazlogin();
            login.setVisible(true);
        });
    }
}
