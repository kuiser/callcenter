package ar.com.almundo.examen.service;

import ar.com.almundo.examen.exception.NoOperatorAvailableException;
import ar.com.almundo.examen.model.EmpleadoCallCenter;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Servicio que maneja la prioridad de Operadores diponible
 */
public class OperatorService {

    private PriorityBlockingQueue<EmpleadoCallCenter> operatorsQueue = new PriorityBlockingQueue<>(10, new EmpleadoCallCenterComparator());

    /**
     * Metodo para agregar empleados para atender en el call Center
     * @param empleadoCallCenter Empleado
     * @return retorna el servicio para poder concantonar
     */
    public OperatorService addOperators(EmpleadoCallCenter empleadoCallCenter){
        operatorsQueue.add(empleadoCallCenter);
        return this;
    }

    /**
     * Retorna el primer operador disponible con prioridad.
     * Si no hay operador disponible lanza una {@link NoOperatorAvailableException}
     * @return operador disponible
     * @throws NoOperatorAvailableException cuando no puede retornar un operador disponible
     */
    public EmpleadoCallCenter getNextOperators() throws NoOperatorAvailableException {

        try {
            EmpleadoCallCenter operator = operatorsQueue.poll(1, TimeUnit.SECONDS);
            if(operator == null)
                throw new NoOperatorAvailableException();
            return operator;
        } catch (InterruptedException e) {
            throw new NoOperatorAvailableException();
        }
    }

    /**
     * Retorna la cantidad de operadores disponibles
     * @return cantidad de operadores
     */
    public Integer operatorCount(){
        return operatorsQueue.size();
    }

    public class EmpleadoCallCenterComparator implements Comparator<EmpleadoCallCenter> {

        @Override
        public int compare(EmpleadoCallCenter o1, EmpleadoCallCenter o2) {
            return o1.getPrioridad().compareTo(o2.getPrioridad());
        }

    }

}
