package unison.mx;

import javax.swing.*;
import java.awt.*;

public class menuPrincipal extends JFrame {

    private CardLayout layout;
    private JPanel panelContenido;

    public menuPrincipal() {

        Usuario u = sesionActual.getUsuario();
        String nombre = (u != null) ? u.getNombre() : "N/A";

        setTitle("Sistema de Inventario - UNISON");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setFont(new Font("Segoe UI", Font.PLAIN, 14));

        initUI();
    }

    private void initUI() {

        JPanel panelBase = new JPanel(new BorderLayout());
        add(panelBase);

        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(new Color(0, 82, 158)); // Azul UNISON
        panelMenu.setPreferredSize(new Dimension(230, 0));
        panelMenu.setLayout(new GridLayout(10, 1, 0, 5));

        JLabel lblTitulo = new JLabel("UNISON", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));

        JButton btnInicio = crearBoton("Inicio");
        JButton btnProductos = crearBoton("Productos");
        JButton btnAlmacenes = crearBoton("Almacenes");
        JButton btnSalir = crearBoton("Cerrar sesión");

        panelMenu.add(lblTitulo);
        panelMenu.add(new JLabel(""));
        panelMenu.add(btnInicio);
        panelMenu.add(btnProductos);
        panelMenu.add(btnAlmacenes);

        panelMenu.add(new JLabel(""));
        panelMenu.add(btnSalir);

        panelBase.add(panelMenu, BorderLayout.WEST);

        panelContenido = new JPanel();
        layout = new CardLayout();
        panelContenido.setLayout(layout);

        inicioPanel inicio = new inicioPanel();
        productosPanel productos = new productosPanel(layout, panelContenido);
        almacenPanel almacenes = new almacenPanel(layout, panelContenido);

        panelContenido.add(inicio, "inicio");
        panelContenido.add(productos, "productos");
        panelContenido.add(almacenes, "almacenes");

        panelContenido.add(inicio, "inicio");
        panelContenido.add(productos, "productos");
        panelContenido.add(almacenes, "almacenes");

        panelBase.add(panelContenido, BorderLayout.CENTER);

        btnInicio.addActionListener(e -> {layout.show(panelContenido, "inicio");});
        btnProductos.addActionListener(e -> layout.show(panelContenido, "productos"));
        btnAlmacenes.addActionListener(e -> layout.show(panelContenido, "almacenes"));

        btnSalir.addActionListener(e -> {
            sesionActual.cerrarSesion();
            new interfazlogin().setVisible(true);
            dispose();
        });
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(0, 82, 158));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 18));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(1, 70, 130));  // azul oscuro
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(0, 82, 158));
            }
        });

        return boton;
    }
}
