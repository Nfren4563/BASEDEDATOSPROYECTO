package unison.mx;

public class manejadorRoles {


    public static boolean canManageProductos() {
        if (sesionActual.getUsuario() == null) return false;
        String r = sesionActual.getUsuario().getRol();
        return "ADMIN".equalsIgnoreCase(r) || "PRODUCTOS".equalsIgnoreCase(r);
    }


    public static boolean canManageAlmacenes() {
        if (sesionActual.getUsuario() == null) return false;
        String r = sesionActual.getUsuario().getRol();
        return "ADMIN".equalsIgnoreCase(r) || "ALMACENES".equalsIgnoreCase(r);
    }

}
