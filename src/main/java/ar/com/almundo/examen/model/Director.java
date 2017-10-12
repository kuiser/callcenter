package ar.com.almundo.examen.model;

public class Director extends Empleado implements EmpleadoCallCenter {

    public Director(Long aLong, String s, String s1) {
        super(aLong, s, s1);
    }

    public Director() {

    }

    @Override
    public Integer getPrioridad() {
        return 30;
    }
}
