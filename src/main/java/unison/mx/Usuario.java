package unison.mx;

public class Usuario {

    private String nombre;
    private String password;
    private String fechaUltimoInicio;
    private String rol;

    public Usuario(String nombre, String password, String fechaUltimoInicio, String rol) {
        this.nombre = nombre;
        this.password = password;
        this.fechaUltimoInicio = fechaUltimoInicio;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    public String getFechaUltimoInicio() {
        return fechaUltimoInicio;
    }

    public String getRol() {
        return rol;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFechaUltimoInicio(String fechaUltimoInicio) {
        this.fechaUltimoInicio = fechaUltimoInicio;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
