package Models;

public class Tipo_suscripcion {
    private int tipo_id;
    private String nombre;
    private float precio;
    private int duracion_meses;

    public Tipo_suscripcion(int tipo_id, String nombre, float precio, int duracion_meses) {
        this.tipo_id = tipo_id;
        this.nombre = nombre;
        this.precio = precio;
        this.duracion_meses = duracion_meses;
    }
    
    public Tipo_suscripcion(){
        this.tipo_id = 0;
        this.nombre = "";
        this.precio = 0;
        this.duracion_meses = 0;
    }

    @Override
    public String toString() {
        return "Tipo_suscripcion{" + "tipo_id=" + tipo_id + ", nombre=" + nombre + ", precio=" + precio + ", duracion_meses=" + duracion_meses + '}';
    }

    public int getTipo_id() {
        return tipo_id;
    }

    public void setTipo_id(int tipo_id) {
        this.tipo_id = tipo_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getDuracion_meses() {
        return duracion_meses;
    }

    public void setDuracion_meses(int duracion_meses) {
        this.duracion_meses = duracion_meses;
    }
 
    
}
