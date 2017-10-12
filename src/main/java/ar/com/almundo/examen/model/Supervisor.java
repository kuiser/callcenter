package ar.com.almundo.examen.model;

public class Supervisor extends Empleado implements EmpleadoCallCenter{

    public Supervisor(Long aLong, String s, String s1) {
        super(aLong, s, s1);
    }

    public Supervisor() {

    }

    @Override
    public Integer getPrioridad() {
        return 20;
    }
}
