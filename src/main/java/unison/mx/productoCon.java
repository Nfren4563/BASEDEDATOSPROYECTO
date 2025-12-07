package unison.mx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class productoCon {

    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();

        String sql = """
                SELECT p.id, p.nombre, p.precio, p.cantidad, p.departamento, 
                       p.almacen, a.nombre AS nombreAlmacen,
                       p.fecha_hora_creacion,
                       p.fecha_hora_ultima_modificacion,
                       p.ultimo_usuario_en_modificar
                FROM productos p
                LEFT JOIN almacenes a ON p.almacen = a.id
                """;

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad"),
                        rs.getString("departamento"),
                        rs.getInt("almacen"),
                        rs.getString("fecha_hora_creacion"),
                        rs.getString("fecha_hora_ultima_modificacion"),
                        rs.getString("ultimo_usuario_en_modificar")
                );

                p.setNombreAlmacen(rs.getString("nombreAlmacen"));

                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }

        return lista;
    }

    public boolean insertar(Producto p) {
        String sql = """
                INSERT INTO productos(nombre, precio, cantidad, departamento, almacen,
                                      fecha_hora_creacion, fecha_hora_ultima_modificacion,
                                      ultimo_usuario_en_modificar)
                VALUES (?, ?, ?, ?, ?, datetime('now','localtime'), datetime('now','localtime'), ?)
                """;

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setInt(3, p.getCantidad());
            ps.setString(4, p.getDepartamento());
            ps.setInt(5, p.getAlmacen());
            ps.setString(6, p.getUltimoUsuarioModifico());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Producto p) {
        String sql = """
                UPDATE productos
                SET nombre=?, precio=?, cantidad=?, departamento=?, almacen=?,
                    fecha_hora_ultima_modificacion=datetime('now','localtime'),
                    ultimo_usuario_en_modificar=?
                WHERE id=?
                """;

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setInt(3, p.getCantidad());
            ps.setString(4, p.getDepartamento());
            ps.setInt(5, p.getAlmacen());
            ps.setString(6, p.getUltimoUsuarioModifico());
            ps.setInt(7, p.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }
    public ArrayList<Producto> buscarConFiltros(
            String nombre,
            String cantidad,
            String precioMin,
            String precioMax,
            String departamento,
            String almacen
    ) {
        ArrayList<Producto> lista = new ArrayList<>();

        String sql = """
            SELECT p.id, p.nombre, p.precio, p.cantidad, p.departamento, 
                   p.almacen, a.nombre AS nombreAlmacen,
                   p.fecha_hora_creacion,
                   p.fecha_hora_ultima_modificacion,
                   p.ultimo_usuario_en_modificar
            FROM productos p
            LEFT JOIN almacenes a ON p.almacen = a.id
            WHERE 1=1
            """;

        ArrayList<Object> params = new ArrayList<>();

        if (!nombre.isEmpty()) {
            sql += " AND p.nombre LIKE ?";
            params.add("%" + nombre + "%");
        }
        if (!cantidad.isEmpty()) {
            sql += " AND p.cantidad = ?";
            params.add(Integer.parseInt(cantidad));
        }
        if (!precioMin.isEmpty()) {
            sql += " AND p.precio >= ?";
            params.add(Double.parseDouble(precioMin));
        }
        if (!precioMax.isEmpty()) {
            sql += " AND p.precio <= ?";
            params.add(Double.parseDouble(precioMax));
        }
        if (!departamento.isEmpty()) {
            sql += " AND p.departamento LIKE ?";
            params.add("%" + departamento + "%");
        }
        if (!almacen.isEmpty()) {
            sql += " AND a.nombre LIKE ?";
            params.add("%" + almacen + "%");
        }

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad"),
                        rs.getString("departamento"),
                        rs.getInt("almacen"),
                        rs.getString("fecha_hora_creacion"),
                        rs.getString("fecha_hora_ultima_modificacion"),
                        rs.getString("ultimo_usuario_en_modificar")
                );

                p.setNombreAlmacen(rs.getString("nombreAlmacen"));

                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Error al buscar productos con filtros: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    }



