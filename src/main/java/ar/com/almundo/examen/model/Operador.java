package ar.com.almundo.examen.model;

public class Operador extends Empleado implements EmpleadoCallCenter{

    public Operador(Long i, String s, String s1) {
        super(i, s, s1);
    }

    public Operador() {

    }

    @Override
    public Integer getPrioridad() {
        return 1;
    }
}
