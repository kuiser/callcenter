package ar.com.almundo.examen.service;

import ar.com.almundo.examen.exception.NoOperatorAvailableException;
import ar.com.almundo.examen.model.Director;
import ar.com.almundo.examen.model.Operador;
import ar.com.almundo.examen.model.Supervisor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


public class OperatorServiceTest {

    private OperatorService operatorService;

    @Before
    public void init(){
        operatorService = new OperatorService();
    }

    @Test
    public void orderTest() throws NoOperatorAvailableException {
        Director director = new Director();
        Operador operador = new Operador();
        Supervisor supervisor = new Supervisor();

        operatorService.addOperators(director).addOperators(operador).addOperators(supervisor);

        Assert.assertTrue(operatorService.getNextOperators() instanceof Operador);
        Assert.assertTrue(operatorService.getNextOperators() instanceof Supervisor);
        Assert.assertTrue(operatorService.getNextOperators() instanceof Director);
    }

    @Test(expected = NoOperatorAvailableException.class)
    public void emptyQueueTest() throws NoOperatorAvailableException {
        operatorService.getNextOperators();
    }
}
