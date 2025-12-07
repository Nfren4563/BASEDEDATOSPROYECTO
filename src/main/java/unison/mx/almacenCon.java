package unison.mx;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class almacenCon {

    public List<Almacen> listar() {
        List<Almacen> lista = new ArrayList<>();
        String sql = """
                SELECT id, nombre,
                       fecha_hora_creacion,
                       fecha_hora_ultima_modificacion,
                       ultimo_usuario_en_modificar
                FROM almacenes
                """;
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Almacen a = new Almacen(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("fecha_hora_creacion"),
                        rs.getString("fecha_hora_ultima_modificacion"),
                        rs.getString("ultimo_usuario_en_modificar")
                );
                lista.add(a);
            }
        } catch (Exception e) {
            System.out.println("Error al listar almacenes: " + e.getMessage());
        }
        return lista;
    }

    public boolean insertar(Almacen a) {
        String sql = """
                INSERT INTO almacenes(nombre, fecha_hora_creacion,
                                      fecha_hora_ultima_modificacion,
                                      ultimo_usuario_en_modificar)
                VALUES (?, datetime('now','localtime'), datetime('now','localtime'), ?)
                """;
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getUltimoUsuarioModifico());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error al insertar almacén: " + e.getMessage());
            return false;
        }
    }
    //AQUI ESTABA LA FALLA DE ELIMINAR ALMACENES, LA ID SE QUEDABA ASOCIADA
    public boolean eliminar(int id) {
        String sqlProductos = "UPDATE productos SET almacen = NULL WHERE almacen = ?";
        String sqlAlmacen = "DELETE FROM almacenes WHERE id = ?";

        Connection con = null;
        try {
            con = ConexionBD.conectar();
            con.setAutoCommit(false); // Iniciar transacción

            try (PreparedStatement psProductos = con.prepareStatement(sqlProductos)) {
                psProductos.setInt(1, id);
                psProductos.executeUpdate();
            }

            try (PreparedStatement psAlmacen = con.prepareStatement(sqlAlmacen)) {
                psAlmacen.setInt(1, id);
                int resultado = psAlmacen.executeUpdate();

                con.commit();
                return resultado > 0;
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar almacén: " + e.getMessage());
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    public boolean actualizar(Almacen a) {
        String sql = """
                UPDATE almacenes
                SET nombre = ?,
                    fecha_hora_ultima_modificacion = datetime('now','localtime'),
                    ultimo_usuario_en_modificar = ?
                WHERE id = ?
                """;
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getUltimoUsuarioModifico());
            ps.setInt(3, a.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error al actualizar almacén: " + e.getMessage());
            return false;
        }

    }


}
