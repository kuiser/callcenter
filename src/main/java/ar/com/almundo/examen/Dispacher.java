package ar.com.almundo.examen;

import ar.com.almundo.examen.exception.NoOperatorAvailableException;
import ar.com.almundo.examen.exception.NotEnoughOperatorException;
import ar.com.almundo.examen.model.Client;
import ar.com.almundo.examen.service.OperatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Clase encargada de manejar las llamadas entrantes
 */
public class Dispacher {

    private final Logger logger = LoggerFactory.getLogger(Dispacher.class);

    private static final Integer MINIMAL_OP = 10;
    private static final Integer MINIMAL_CONCURRENT_CALL = 10;

    private OperatorService operatorService;
    private ThreadPoolExecutor executor ;

    /**
     * Crea un dispacher
     * @param operatorService Servicio que maneja los operadores, necesita por lo menos 10
     * @throws NotEnoughOperatorException
     */
    public Dispacher(OperatorService operatorService) throws NotEnoughOperatorException {

        if(operatorService.operatorCount() < MINIMAL_OP ){
            throw new NotEnoughOperatorException();
        }

        this.operatorService = operatorService;

        RejectedCall rejectionHandler = new RejectedCall();

        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //creating the ThreadPoolExecutor
        executor = new DispacherThreadPoolExecutor(MINIMAL_CONCURRENT_CALL,
                MINIMAL_CONCURRENT_CALL,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                threadFactory,
                rejectionHandler);
    }

    /**
     * Metodo que atiende una llamada
     * @param client Cliente a atender
     * @throws NoOperatorAvailableException
     */
    public void dispatchCall(Client client) throws NoOperatorAvailableException {
        executor.execute(new Call(client, operatorService, null));
    }

    /**
     * Metodo para apagar el dispacher
     */
    public void shutdownDispatcher(){
        executor.shutdown();
    }

    /**
     * Metodo que retorna TRUE si existe llamadas en activas. FALSE si el Dispacher esta idle
     * @return
     */
    public Boolean isStillProcessing(){
        return executor.getActiveCount() > 0;
    }

    /**
     * ThreadPool especifico para el Dispacher. Tiene manejos de excepciones para poder reintentar las llamadas
     */
    private class DispacherThreadPoolExecutor extends ThreadPoolExecutor {

        private final Logger logger = LoggerFactory.getLogger(DispacherThreadPoolExecutor.class);

        public DispacherThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        /**
         * Se ejecuta despues de una excepcion. Checquea que sea del tipo {@link NoOperatorAvailableException}
         * y reintenta la llamada
         * @param r La llamada {@link Call}
         * @param t la excepcion producida.
         */
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            if(t != null){
                if(t instanceof NoOperatorAvailableException) {
                    Call call = (Call) r;
                    logger.debug("No hay operador disponible para " + call.getClient());
                    executor.execute(r);
                }else{
                    logger.debug("Ha ocurrido una excepcion", t);
                }

            }
        }
    }

}
