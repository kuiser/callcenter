package ar.com.almundo.examen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class RejectedCall implements RejectedExecutionHandler {

    private final Logger logger = LoggerFactory.getLogger(RejectedCall.class);

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        Call call = (Call) r;

        if(call.retry()){
            logger.debug("La llamada de cliente " + call.getClient() + " se REINTENTARA atender. Quedan " + call.remainRetry() + " reintentos");
            executor.execute(r);
        }else{
            logger.debug("La llamada de cliente " + call.getClient() + " se RECAHZO");
        }

    }

}
