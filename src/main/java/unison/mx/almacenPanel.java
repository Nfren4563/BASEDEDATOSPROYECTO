package unison.mx;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class almacenPanel extends JPanel {

    private JTable tabla;
    private DefaultTableModel modelo;
    private TableRowSorter<DefaultTableModel> sorter;
    private almacenCon daoAlmacen = new almacenCon();

    private CardLayout layoutPadre;
    private JPanel contenedor;

    public almacenPanel(CardLayout layoutPadre, JPanel contenedor) {
        this.layoutPadre = layoutPadre;
        this.contenedor = contenedor;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelSuperior = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Gestión de Almacenes", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(new Color(0x00529E));
        panelSuperior.add(titulo, BorderLayout.NORTH);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar:"));
        JTextField txtBuscar = new JTextField(20);
        panelBusqueda.add(txtBuscar);

        panelSuperior.add(panelBusqueda, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Creación", "Modificado", "Usuario"}, 0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(25);

        sorter = new TableRowSorter<>(modelo);
        tabla.setRowSorter(sorter);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnAgregar = new JButton("Agregar almacén");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);

        add(panelBotones, BorderLayout.SOUTH);

        btnAgregar.setEnabled(manejadorRoles.canManageAlmacenes());
        btnModificar.setEnabled(manejadorRoles.canManageAlmacenes());
        btnEliminar.setEnabled(manejadorRoles.canManageAlmacenes());

        btnAgregar.addActionListener(e -> abrirFormulario(null));
        btnModificar.addActionListener(e -> modificarAlmacen());
        btnEliminar.addActionListener(e -> eliminarAlmacen());
        btnActualizar.addActionListener(e -> cargarTabla());


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

        List<Almacen> lista = daoAlmacen.listar();

        for (Almacen a : lista) {
            modelo.addRow(new Object[]{
                    a.getId(),
                    a.getNombre(),
                    a.getFechaCreacion(),
                    a.getFechaUltModificacion(),
                    a.getUltimoUsuarioModifico()
            });
        }
    }

    private void abrirFormulario(Almacen editar) {
        formularioAlmacenes form = new formularioAlmacenes(
                editar,
                () -> {
                    cargarTabla();
                    layoutPadre.show(contenedor, "almacenes");
                }
        );

        contenedor.add(form, "formAlmacen");
        layoutPadre.show(contenedor, "formAlmacen");
    }

    private void modificarAlmacen() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un almacén.");
            return;
        }

        fila = tabla.convertRowIndexToModel(fila);

        int id = (int) modelo.getValueAt(fila, 0);

        Almacen a = daoAlmacen.listar().stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElse(null);

        abrirFormulario(a);
    }

    private void eliminarAlmacen() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un almacén.");
            return;
        }

        fila = tabla.convertRowIndexToModel(fila);

        int id = (int) modelo.getValueAt(fila, 0);

        if (JOptionPane.showConfirmDialog(this,
                "¿Eliminar almacén ID " + id + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            if (daoAlmacen.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Eliminado.");
                cargarTabla();
            }
        }
    }
}
