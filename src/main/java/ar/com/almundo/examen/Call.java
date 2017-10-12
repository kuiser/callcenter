package ar.com.almundo.examen;

import ar.com.almundo.examen.model.Client;
import ar.com.almundo.examen.model.EmpleadoCallCenter;
import ar.com.almundo.examen.service.OperatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Clase que procesar la llamada
 */
public class Call implements Runnable{

    private final Logger logger = LoggerFactory.getLogger(Dispacher.class);

    private Client client;
    private OperatorService operatorService;
    private Integer retry;
    private Integer tryCount;

    /**
     *
     * @param client El cliente que realiza la llamada
     * @param operatorService Servicio que entrega el siguiente operador disponible
     * @param numberOfRetries numero de reintentos hasta atender la llamada. si es null entonces intenta indefinidamente
     */
    public Call(Client client, OperatorService operatorService, Integer numberOfRetries){
        this.client = client;
        this.operatorService = operatorService;
        this.retry = numberOfRetries;
        this.tryCount = 0;
    }

    @Override
    public void run() {

        EmpleadoCallCenter nextOperators = operatorService.getNextOperators();

        logger.debug("Atendiendo llamada " + getClient() + " atendida por: " + nextOperators);

        tryCount++;

        try {
            Thread.sleep(callDurationSimulation());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.debug("Cortando llamada: " + getClient());
        operatorService.addOperators(nextOperators);
    }

    /**
     * Retorna el cliente que esta efectuando la llamada.
     * @return
     */
    public Client getClient() {
        return client;
    }

    /**
     * Informa si la llamada puede ser reintentada.
     * @return
     */
    public Boolean retry() {
        return this.retry == null || this.retry > 0;
    }

    /**
     * Retorna la cantidad de reintentos restantes
     * @return cantidad de reintentos restantes
     */
    public Integer remainRetry(){
        return this.retry;
    }

    private int callDurationSimulation(){
        int  n = new Random().nextInt(5001);
        return n + 5000;
    }

    /**
     * Retorna la cantidad de veces que se intentó hacer la llamada.
     * @return intentos
     */
    public Integer getTryCount(){
        return tryCount;
    }

}
