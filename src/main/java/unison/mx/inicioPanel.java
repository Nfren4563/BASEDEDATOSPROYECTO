package unison.mx;

import javax.swing.*;
import java.awt.*;

public class inicioPanel extends JPanel {

    private JLabel lblUsuario;

    public inicioPanel() {

        setLayout(new BorderLayout());
        setBackground(new Color(0xF2F2F2));


        JLabel titulo = new JLabel("Sistema de Inventario - UNISON", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(new Color(0x00529E));

        add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);
        add(centro, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);

        ImageIcon logo = null;
        try {
            logo = new ImageIcon(getClass().getResource("/unison.png"));
        } catch (Exception e) {
            System.out.println("No se encontró la imagen unison.png en resources.");
        }

        JLabel lblLogo = new JLabel();
        if (logo != null) {
            Image img = logo.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } else {
            lblLogo.setText("LOGO NO ENCONTRADO");
            lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        }

        gbc.gridy = 0;
        centro.add(lblLogo, gbc);


        JLabel desarrolladoPor = new JLabel("Desarrollado por: Efren Alejandro Gonzalez");
        desarrolladoPor.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        desarrolladoPor.setForeground(new Color(0x003F7F));

        gbc.gridy = 1;
        centro.add(desarrolladoPor, gbc);


        lblUsuario = new JLabel();
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblUsuario.setForeground(Color.BLACK);

        gbc.gridy = 2;
        centro.add(lblUsuario, gbc);

        actualizarUsuario();
    }

    public void actualizarUsuario() {
        Usuario u = sesionActual.getUsuario();
        if (u == null) {
            lblUsuario.setText("Usuario actual: N/A");
        } else {
            lblUsuario.setText("Usuario actual: " + u.getNombre());
        }
    }
}
