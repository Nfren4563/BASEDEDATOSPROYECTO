package unison.mx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class usuarioUltSes {

    public static Usuario buscarUsuario(String nombre) {
        String sql = "SELECT nombre, password, fecha_hora_ultimo_inicio, rol FROM usuarios WHERE nombre=?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getString("nombre"),
                        rs.getString("password"),
                        rs.getString("fecha_hora_ultimo_inicio"),
                        rs.getString("rol")
                );
            }

        } catch (Exception e) {
            System.out.println("Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }

    public static void actualizarUltimoInicio(String usuario) {
        String sql = "UPDATE usuarios SET fecha_hora_ultimo_inicio=datetime('now') WHERE nombre=?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error al actualizar último inicio: " + e.getMessage());
        }
    }
}
