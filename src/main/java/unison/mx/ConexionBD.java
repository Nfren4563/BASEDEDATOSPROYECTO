package unison.mx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.File;

public class ConexionBD {

    private static final String DB_FILENAME = "InventarioBD_2.db";

    public static Connection conectar() {
        try {

            String jarPath = new File("").getAbsolutePath();
            String fullPath = jarPath + File.separator + DB_FILENAME;

            String url = "jdbc:sqlite:" + fullPath;

            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(url);

        } catch (Exception e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }
}
