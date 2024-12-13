package tst;

import app.IRPF;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TesteAliquotaEfetiva {

    IRPF irpf;
    float salario, bolsa, resultadoSemDeducao, resultadoComDeducao;

    @Before
    public void setup() {
        irpf = new IRPF();
    }

    @Parameters
    public static Collection<Object[]> getParameters() {
        Object[][] parametros = new Object[][] {
                {10000f, 1000f, 18.65f, 17.08f},
                {5000f, 750f, 9.8f, 6.9f},
                {2700f, 700f, 1.63f, 0.05f}
        };
        return Arrays.asList(parametros);

    }

    public TesteAliquotaEfetiva(float salario, float bolsa, float resultadoSemDeducao, float resultadoComDeducao) {
        this.salario = salario;
        this.bolsa = bolsa;
        this.resultadoSemDeducao = resultadoSemDeducao;
        this.resultadoComDeducao = resultadoComDeducao;
    }

    @Test
    public void testCalculoDaAliquotaEfetiva() {
        irpf.criarRendimento("Salario", IRPF.TRIBUTAVEL, salario);

        assertEquals(resultadoSemDeducao, irpf.getAliquotaEfetiva(), 0.01f);
    }

    @Test
    public void testeCalculoDaAliquotaEfetivaComNaoTributaveis() {
        irpf.criarRendimento("Salario", IRPF.TRIBUTAVEL, salario);
        irpf.criarRendimento("Bolsa de Estudos", IRPF.NAOTRIBUTAVEL, bolsa);

        assertEquals(resultadoSemDeducao, irpf.getAliquotaEfetiva(), 0.01f);
    }

    @Test
    public void testeCalculoDaAliquotaEfetivaComDeducao() {
        irpf.criarRendimento("Salario", IRPF.TRIBUTAVEL, salario);
        irpf.cadastrarDependente("Valdecir", "Filho");
        irpf.cadastrarDependente("Eduardo", "Filha");
        irpf.cadastrarDependente("Cleber", "Filho");

        assertEquals(resultadoComDeducao, irpf.getAliquotaEfetiva(), 0.01f);
    }

}
