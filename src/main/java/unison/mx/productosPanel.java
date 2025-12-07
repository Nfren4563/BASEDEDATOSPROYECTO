package unison.mx;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class productosPanel extends JPanel {

    private JTable tabla;
    private DefaultTableModel modelo;
    private productoCon daoProducto = new productoCon();
    private TableRowSorter<DefaultTableModel> sorter;

    private CardLayout layoutPadre;
    private JPanel contenedor;

    public productosPanel(CardLayout layoutPadre, JPanel contenedor) {
        this.layoutPadre = layoutPadre;
        this.contenedor = contenedor;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelSuperior = new JPanel(new BorderLayout());

        JPanel panelTitulo = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Productos", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(new Color(0x00529E));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelTitulo.add(titulo, BorderLayout.CENTER);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar:"));
        JTextField txtBuscar = new JTextField(20);
        panelBusqueda.add(txtBuscar);

        panelSuperior.add(panelBusqueda, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
                new String[]{
                        "ID", "Nombre", "Precio", "Cantidad",
                        "Departamento", "Almacén", "Creación",
                        "Modificado", "Usuario"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(25);

        sorter = new TableRowSorter<>(modelo);
        tabla.setRowSorter(sorter);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);

        add(panelBotones, BorderLayout.SOUTH);

        btnAgregar.setEnabled(manejadorRoles.canManageProductos());
        btnModificar.setEnabled(manejadorRoles.canManageProductos());
        btnEliminar.setEnabled(manejadorRoles.canManageProductos());

        btnAgregar.addActionListener(e -> abrirFormulario(null));
        btnModificar.addActionListener(e -> modificarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnActualizar.addActionListener(e -> cargarTabla());


        JPanel panelFiltros = new JPanel();
        panelFiltros.setLayout(new BorderLayout());
        panelFiltros.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(0x00529E), 2),
                        " Filtros de Búsqueda ",
                        0,
                        0,
                        new Font("Segoe UI", Font.BOLD, 13),
                        new Color(0x00529E)
                )
        ));
        panelFiltros.setBackground(new Color(0xF5F5F5));

        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new GridBagLayout());
        panelCampos.setBackground(new Color(0xF5F5F5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtNombre = new JTextField(15);
        JTextField txtCantidad = new JTextField(10);
        JTextField txtPrecioMin = new JTextField(10);
        JTextField txtPrecioMax = new JTextField(10);
        JTextField txtDepartamento = new JTextField(15);

        JComboBox<String> comboAlmacen = new JComboBox<>();
        comboAlmacen.addItem("-- Todos --");

        try {
            almacenCon daoAlmacen = new almacenCon();
            List<Almacen> almacenes = daoAlmacen.listar();
            for (Almacen alm : almacenes) {
                comboAlmacen.addItem(alm.getNombre());
            }
        } catch (Exception ex) {
            System.out.println("Error al cargar almacenes: " + ex.getMessage());
        }


        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelCampos.add(lblNombre, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        panelCampos.add(txtNombre, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        gbc.insets = new Insets(5, 30, 5, 10);
        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelCampos.add(lblCantidad, gbc);

        gbc.gridx = 3; gbc.weightx = 0.5;
        gbc.insets = new Insets(5, 10, 5, 10);
        panelCampos.add(txtCantidad, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblPrecioMin = new JLabel("Precio mínimo:");
        lblPrecioMin.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelCampos.add(lblPrecioMin, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        panelCampos.add(txtPrecioMin, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        gbc.insets = new Insets(5, 30, 5, 10);
        JLabel lblPrecioMax = new JLabel("Precio máximo:");
        lblPrecioMax.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelCampos.add(lblPrecioMax, gbc);

        gbc.gridx = 3; gbc.weightx = 0.5;
        gbc.insets = new Insets(5, 10, 5, 10);
        panelCampos.add(txtPrecioMax, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        JLabel lblDepartamento = new JLabel("Departamento:");
        lblDepartamento.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelCampos.add(lblDepartamento, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        panelCampos.add(txtDepartamento, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        gbc.insets = new Insets(5, 30, 5, 10);
        JLabel lblAlmacen = new JLabel("Almacén:");
        lblAlmacen.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelCampos.add(lblAlmacen, gbc);

        gbc.gridx = 3; gbc.weightx = 0.5;
        gbc.insets = new Insets(5, 10, 5, 10);
        panelCampos.add(comboAlmacen, gbc);

        panelFiltros.add(panelCampos, BorderLayout.CENTER);

        JPanel panelBotonesFiltro = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotonesFiltro.setBackground(new Color(0xF5F5F5));

        JButton btnBuscar = new JButton("🔍 Buscar");
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBuscar.setBackground(new Color(0x4CAF50));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setPreferredSize(new Dimension(130, 35));
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnLimpiar = new JButton("🗑️ Limpiar");
        btnLimpiar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLimpiar.setBackground(new Color(0xFF9800));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.setPreferredSize(new Dimension(130, 35));
        btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBotonesFiltro.add(btnBuscar);
        panelBotonesFiltro.add(btnLimpiar);

        panelFiltros.add(panelBotonesFiltro, BorderLayout.SOUTH);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelTitulo, BorderLayout.NORTH);
        panelNorte.add(panelFiltros, BorderLayout.CENTER);

        add(panelNorte, BorderLayout.NORTH);

        btnBuscar.addActionListener(e -> {
            String n = txtNombre.getText().trim();
            String c = txtCantidad.getText().trim();
            String p1 = txtPrecioMin.getText().trim();
            String p2 = txtPrecioMax.getText().trim();
            String d = txtDepartamento.getText().trim();

            String almacenSeleccionado = (String) comboAlmacen.getSelectedItem();
            String a = "";
            if (almacenSeleccionado != null && !almacenSeleccionado.equals("-- Todos --")) {
                a = almacenSeleccionado;
            }

            productoCon dao = new productoCon();
            ArrayList<Producto> lista = dao.buscarConFiltros(n, c, p1, p2, d, a);

            modelo.setRowCount(0);
            for (Producto p : lista) {
                modelo.addRow(new Object[]{
                        p.getId(), p.getNombre(), p.getPrecio(),
                        p.getCantidad(), p.getDepartamento(),
                        p.getNombreAlmacen(),
                        p.getFechaCreacion(), p.getFechaUltModificacion(),
                        p.getUltimoUsuarioModifico()
                });
            }

            JOptionPane.showMessageDialog(this,
                    "Se encontraron " + lista.size() + " producto(s)",
                    "Resultados",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        btnLimpiar.addActionListener(e -> {
            txtNombre.setText("");
            txtCantidad.setText("");
            txtPrecioMin.setText("");
            txtPrecioMax.setText("");
            txtDepartamento.setText("");
            comboAlmacen.setSelectedIndex(0); // Volver a "-- Todos --"
            cargarTabla(); // Volver a mostrar todos los productos
            JOptionPane.showMessageDialog(this,
                    "Filtros limpiados. Mostrando todos los productos.",
                    "Filtros",
                    JOptionPane.INFORMATION_MESSAGE);
        });




        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filtrar(txtBuscar.getText()); }
            @Override public void removeUpdate(DocumentEvent e) { filtrar(txtBuscar.getText()); }
            @Override public void changedUpdate(DocumentEvent e) { filtrar(txtBuscar.getText()); }
        });

        cargarTabla();
    }

    private void filtrar(String texto) {
        if (texto.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
        }
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        List<Producto> lista = daoProducto.listarTodos();

        for (Producto p : lista) {
            modelo.addRow(new Object[]{
                    p.getId(), p.getNombre(), p.getPrecio(),
                    p.getCantidad(), p.getDepartamento(),
                    p.getNombreAlmacen(),
                    p.getFechaCreacion(), p.getFechaUltModificacion(),
                    p.getUltimoUsuarioModifico()
            });
        }
    }

    private void abrirFormulario(Producto editar) {
        formularioProductos form = new formularioProductos(
                editar,
                () -> {
                    cargarTabla();
                    layoutPadre.show(contenedor, "productos");
                }
        );

        contenedor.add(form, "formProducto");
        layoutPadre.show(contenedor, "formProducto");
    }

    private void modificarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.");
            return;
        }

        Producto p = daoProducto.listarTodos().get(tabla.convertRowIndexToModel(fila));
        abrirFormulario(p);
    }

    private void eliminarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.");
            return;
        }

        int id = (int) tabla.getValueAt(fila, 0);

        if (JOptionPane.showConfirmDialog(this,
                "¿Eliminar producto ID " + id + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            if (daoProducto.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Producto eliminado.");
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar.");
            }
        }
    }
}
