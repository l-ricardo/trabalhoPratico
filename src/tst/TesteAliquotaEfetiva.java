package tst;

import app.IRPF;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;

/*
 * Todo:
 * - Parametrizar os testes
 */

//@RunWith(Parameterized.class)
public class TesteAliquotaEfetiva {

    IRPF irpf;

    @Before
    public void setup() {
        irpf = new IRPF();
    }

    @Test
    public void testAliquotaEfetiva() {
        irpf.criarRendimento("Salario", IRPF.TRIBUTAVEL, 10000f);

        assertEquals(18.53f, irpf.getAliquotaEfetiva(), 0.01f);
    }

    @Test
    public void testOutraAliquotaEfetiva() {
        irpf.criarRendimento("Salario", IRPF.TRIBUTAVEL, 5000f);

        assertEquals(9.57f, irpf.getAliquotaEfetiva(), 0.01f);
    }

}
