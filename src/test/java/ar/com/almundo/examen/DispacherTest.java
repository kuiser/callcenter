package ar.com.almundo.examen;

import ar.com.almundo.examen.exception.NoOperatorAvailableException;
import ar.com.almundo.examen.exception.NotEnoughOperatorException;
import ar.com.almundo.examen.model.Client;
import ar.com.almundo.examen.model.Director;
import ar.com.almundo.examen.model.Operador;
import ar.com.almundo.examen.model.Supervisor;
import ar.com.almundo.examen.service.OperatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class DispacherTest {

    @Test
    public void dispacherTest() throws NoOperatorAvailableException, NotEnoughOperatorException {


        OperatorService operatorService = new OperatorService();

        for(int i = 1 ; i <= 1; i++){
            operatorService.addOperators(new Director(new Long(i), "Director " + i, "Director " + i));
        }

        for(int i = 2 ; i <= 3; i++){
            operatorService.addOperators(new Supervisor(new Long(i), "Supervisor " + i, "Supervisor " + i));
        }

        for(int i = 4 ; i <= 10; i++){
            operatorService.addOperators(new Operador(new Long(i), "Operador " + i, "Operador " + i));
        }



        Dispacher dispacher = new Dispacher(operatorService);

        for (int i = 1; i <= 20; i++){
            dispacher.dispatchCall( new Client(i, "Cliente " + 1, "Cliente " + 1));
        }


        while (dispacher.isStillProcessing()) {}

    }

    @Test(expected = NotEnoughOperatorException.class)
    public void emptyOperationServiceTest() throws NotEnoughOperatorException {
        OperatorService operatorService = new OperatorService();

       new Dispacher(operatorService);
    }


}
