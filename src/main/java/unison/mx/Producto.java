package unison.mx;

public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int cantidad;
    private String departamento;
    private int almacen;
    private String nombreAlmacen;

    private String fechaCreacion;
    private String fechaUltModificacion;
    private String ultimoUsuarioModifico;

    public Producto(int id, String nombre, double precio, int cantidad, String departamento, int almacen,
                    String fechaCreacion, String fechaUltModificacion, String ultimoUsuarioModifico) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.departamento = departamento;
        this.almacen = almacen;
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

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public int getAlmacen() {
        return almacen;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public String getFechaUltModificacion() {
        return fechaUltModificacion;
    }

    public String getUltimoUsuarioModifico() {
        return ultimoUsuarioModifico;
    }
    public String getNombreAlmacen() {
        return nombreAlmacen;
    }
    public void setNombreAlmacen(String nombreAlmacen) {
        this.nombreAlmacen = nombreAlmacen;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setAlmacen(int almacen) {
        this.almacen = almacen;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaUltModificacion(String fechaUltModificacion) {
        this.fechaUltModificacion = fechaUltModificacion;
    }

    public void setUltimoUsuarioModifico(String ultimoUsuarioModifico) {
        this.ultimoUsuarioModifico = ultimoUsuarioModifico;
    }
}
