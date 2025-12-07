package unison.mx;

import javax.swing.*;
import java.awt.*;

public class formularioAlmacenes extends JPanel {

    private JTextField txtNombre;

    private almacenCon daoAlmacen = new almacenCon();

    private Almacen almacenEdit;
    private Runnable onFinish;

    public formularioAlmacenes(Almacen editar, Runnable onFinish) {
        this.almacenEdit = editar;
        this.onFinish = onFinish;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel(
                editar == null ? "Agregar Almacén" : "Modificar Almacén",
                SwingConstants.CENTER
        );
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));

        txtNombre = new JTextField();
        form.add(new JLabel("Nombre:"));
        form.add(txtNombre);

        if (editar != null) {
            txtNombre.setText(editar.getNombre());
        }

        add(form, BorderLayout.CENTER);

        JPanel botones = new JPanel();
        JButton btnGuardar = new JButton(editar == null ? "Guardar" : "Modificar");
        JButton btnCancelar = new JButton("Cancelar");

        botones.add(btnGuardar);
        botones.add(btnCancelar);

        add(botones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> onFinish.run());
    }

    private void guardar() {
        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre inválido.");
            return;
        }

        if (almacenEdit == null) {
            Almacen nuevo = new Almacen(
                    0,
                    nombre,
                    "",
                    "",
                    sesionActual.getUsuario().getNombre()
            );

            if (daoAlmacen.insertar(nuevo)) {
                JOptionPane.showMessageDialog(this, "Almacén agregado.");
                onFinish.run();
            }

        } else {
            almacenEdit = new Almacen(
                    almacenEdit.getId(),
                    nombre,
                    almacenEdit.getFechaCreacion(),
                    "",
                    sesionActual.getUsuario().getNombre()
            );

            if (daoAlmacen.actualizar(almacenEdit)) {
                JOptionPane.showMessageDialog(this, "Almacén modificado.");
                onFinish.run();
            }
        }
    }
}
