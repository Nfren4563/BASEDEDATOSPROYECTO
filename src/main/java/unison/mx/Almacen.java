package unison.mx;

public class Almacen {
    private int id;
    private String nombre;

    private String fechaCreacion;
    private String fechaUltModificacion;
    private String ultimoUsuarioModifico;

    public Almacen(int id, String nombre, String fechaCreacion, String fechaUltModificacion,
                   String ultimoUsuarioModifico) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.fechaUltModificacion = fechaUltModificacion;
        this.ultimoUsuarioModifico = ultimoUsuarioModifico;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public String getFechaUltModificacion() {
        return fechaUltModificacion;
    }
    public void setFechaCreacion(String fechaCreacion) {this.fechaCreacion = fechaCreacion;}

    public String getUltimoUsuarioModifico() {
        return ultimoUsuarioModifico;
    }
    public void setUltimoUsuarioModifico(String ultimoUsuarioModifico) {this.ultimoUsuarioModifico = ultimoUsuarioModifico;}
}
