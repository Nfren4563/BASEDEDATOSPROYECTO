package unison.mx;

import javax.swing.*;
import java.awt.*;

public class interfazlogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public interfazlogin() {
        super("Inicio de Sesión");

        initUI();

        setSize(480, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private void initUI() {

        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(Color.decode("#EEEEEE"));
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(background);

        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(0, 0, 0, 40));
                g2.fillRoundRect(4, 6, getWidth() - 8, getHeight() - 8, 22, 22);

                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 22, 22);

                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(380, 280));
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createEmptyBorder(30, 35, 30, 35));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        JLabel titulo = new JLabel("Iniciar sesión");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Color.decode("#00529e"));
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(titulo, gbc);

        gbc.gridy = 1;
        JLabel lblUser = new JLabel("Usuario");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUser.setForeground(Color.decode("#444444"));
        card.add(lblUser, gbc);

        gbc.gridy = 2;
        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtUsuario.setBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#AAAAAA"))
        );
        txtUsuario.setBackground(Color.WHITE);
        card.add(txtUsuario, gbc);

        gbc.gridy = 3;
        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPass.setForeground(Color.decode("#444444"));
        card.add(lblPass, gbc);

        gbc.gridy = 4;
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtPassword.setBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#AAAAAA"))
        );
        txtPassword.setBackground(Color.WHITE);
        card.add(txtPassword, gbc);

        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        btnLogin = new JButton("Entrar");

        btnLogin.setPreferredSize(new Dimension(150, 40));
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(Color.decode("#015294"));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setFocusPainted(false);

        btnLogin.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        btnLogin.setContentAreaFilled(false);

        btnLogin = new JButton("Iniciar sesión") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(Color.decode("#00529e"));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);

                super.paintComponent(g);
                g2.dispose();
            }

            @Override
            public void setContentAreaFilled(boolean b) {}
        };

        btnLogin.setPreferredSize(new Dimension(160, 42));
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnLogin.setForeground(Color.BLUE);
        btnLogin.addActionListener(e -> login());

        card.add(btnLogin, gbc);

        background.add(card);
    }

    private void login() {
        String usuario = txtUsuario.getText().trim();
        String pass = String.valueOf(txtPassword.getPassword());

        Usuario u = usuarioUltSes.buscarUsuario(usuario);

        if (u == null) {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado");
            return;
        }

        String passHash = encriptacionPassword.md5(pass);

        if (!passHash.equals(u.getPassword())) {
            JOptionPane.showMessageDialog(this, "Contraseña incorrecta");
            return;
        }

        sesionActual.iniciarSesion(u);

        usuarioUltSes.actualizarUltimoInicio(usuario);

        new menuPrincipal().setVisible(true);
        dispose();
    }
}
