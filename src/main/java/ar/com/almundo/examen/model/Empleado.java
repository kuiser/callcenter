package ar.com.almundo.examen.model;

public abstract class Empleado {

    private Long legajo;
    private String nombre;
    private String apellido;

    public Empleado(){}

    public Empleado(Long legajo, String nombre, String apellido){
        this.legajo = legajo;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Long getLegajo() {
        return legajo;
    }

    public void setLegajo(Long legajo) {
        this.legajo = legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return new StringBuffer("[")
                .append("Legajo: ")
                .append(this.legajo)
                .append(" ,")
                .append("Nombre: ")
                .append(this.nombre)
                .append(" ,")
                .append("Apellido: ")
                .append(this.apellido)
                .append("]").toString();
    }
}
