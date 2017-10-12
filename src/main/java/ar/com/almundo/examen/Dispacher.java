package ar.com.almundo.examen;

import ar.com.almundo.examen.exception.NoOperatorAvailableException;
import ar.com.almundo.examen.exception.NotEnoughOperatorException;
import ar.com.almundo.examen.model.Client;
import ar.com.almundo.examen.service.OperatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class Dispacher {

    private static final Integer MINIMAL_OP = 10;
    private static final Integer MINIMAL_CONCURRENT_CALL = 10;
    private final Logger logger = LoggerFactory.getLogger(Dispacher.class);

    private OperatorService operatorService;
    private ThreadPoolExecutor executor ;//= Executors.newFixedThreadPool(10);

    /**
     *
     * @param operatorService
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

    public void dispatchCall(Client client) throws NoOperatorAvailableException {
        executor.execute(new Call(client, operatorService, null));
    }

    public void shutdownDispatcher(){
        executor.shutdown();
    }

    public Boolean isStillProcessing(){
        return executor.getActiveCount() > 0;
    }

    private class DispacherThreadPoolExecutor extends ThreadPoolExecutor {

        private final Logger logger = LoggerFactory.getLogger(DispacherThreadPoolExecutor.class);

        public DispacherThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

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
