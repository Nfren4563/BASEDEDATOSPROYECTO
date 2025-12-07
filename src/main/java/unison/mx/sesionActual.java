package unison.mx;

public class sesionActual {

    private static Usuario usuarioActual;

    public static void iniciarSesion(Usuario u) {
        usuarioActual = u;
    }

    public static Usuario getUsuario() {
        return usuarioActual;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }
}
