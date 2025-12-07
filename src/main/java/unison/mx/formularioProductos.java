package unison.mx;

import javax.swing.*;
import java.awt.*;

public class formularioProductos extends JPanel {

    private JTextField txtNombre, txtPrecio, txtCantidad, txtDepartamento;
    private JComboBox<String> comboAlmacen;
    private JButton btnGuardar, btnCancelar;

    private productoCon daoProducto = new productoCon();
    private almacenCon daoAlmacen = new almacenCon();

    private Producto productoEditando = null;
    private Runnable onFinish;

    public formularioProductos(Producto productoEditar, Runnable onFinish) {
        this.productoEditando = productoEditar;
        this.onFinish = onFinish;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel(
                (productoEditar == null ? "Agregar Producto" : "Modificar Producto"),
                SwingConstants.CENTER
        );
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));

        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        txtCantidad = new JTextField();
        txtDepartamento = new JTextField();

        comboAlmacen = new JComboBox<>();
        daoAlmacen.listar().forEach(a -> comboAlmacen.addItem(a.getNombre()));

        form.add(new JLabel("Nombre:"));
        form.add(txtNombre);

        form.add(new JLabel("Precio:"));
        form.add(txtPrecio);

        form.add(new JLabel("Cantidad:"));
        form.add(txtCantidad);

        form.add(new JLabel("Departamento:"));
        form.add(txtDepartamento);

        form.add(new JLabel("Almacén:"));
        form.add(comboAlmacen);

        add(form, BorderLayout.CENTER);

        JPanel botones = new JPanel();
        btnGuardar = new JButton(productoEditando == null ? "Guardar" : "Modificar");
        btnCancelar = new JButton("Cancelar");

        botones.add(btnGuardar);
        botones.add(btnCancelar);

        add(botones, BorderLayout.SOUTH);

        if (productoEditando != null) {
            txtNombre.setText(productoEditando.getNombre());
            txtPrecio.setText(String.valueOf(productoEditando.getPrecio()));
            txtCantidad.setText(String.valueOf(productoEditando.getCantidad()));
            txtDepartamento.setText(productoEditando.getDepartamento());
            comboAlmacen.setSelectedIndex(Math.max(0, productoEditando.getAlmacen() - 1));
        }

        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> {
            if (onFinish != null) onFinish.run();
        });
    }

    private void guardar() {
        try {
            String nombre = txtNombre.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            String departamento = txtDepartamento.getText().trim();
            int almacen = comboAlmacen.getSelectedIndex() + 1;

            if (nombre.isEmpty() || departamento.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.");
                return;
            }

            if (productoEditando == null) {
                Producto nuevo = new Producto(
                        0, nombre, precio, cantidad, departamento, almacen, "", "", sesionActual.getUsuario().getNombre()
                );

                if (daoProducto.insertar(nuevo)) {
                    JOptionPane.showMessageDialog(this, "Producto agregado.");
                    if (onFinish != null) onFinish.run();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar.");
                }
            } else {
                productoEditando.setNombre(nombre);
                productoEditando.setPrecio(precio);
                productoEditando.setCantidad(cantidad);
                productoEditando.setDepartamento(departamento);
                productoEditando.setAlmacen(almacen);
                productoEditando.setUltimoUsuarioModifico(sesionActual.getUsuario().getNombre());

                if (daoProducto.actualizar(productoEditando)) {
                    JOptionPane.showMessageDialog(this, "Producto modificado.");
                    if (onFinish != null) onFinish.run();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al modificar.");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Datos inválidos.");
        }
    }
}
