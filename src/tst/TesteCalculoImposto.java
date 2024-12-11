package tst;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import app.IRPF;

@RunWith(Parameterized.class)
public class TesteCalculoImposto {

    IRPF irpf;

    private float salario;
    private float aluguel;
    private float bolsa;
    private float contribuicaoPrevidenciaria;
    private float deducaoPrevidenciaPrivada;
    private float pensaoAlimenticia;
    private float impostoEsperado;

    public TesteCalculoImposto(float salario, float aluguel, float bolsa, float contribuicaoPrevidenciaria, float deducaoPrevidenciaPrivada, float pensaoAlimenticia, float impostoEsperado) {
        this.salario = salario;
        this.aluguel = aluguel;
        this.bolsa = bolsa;
        this.contribuicaoPrevidenciaria = contribuicaoPrevidenciaria;
        this.deducaoPrevidenciaPrivada = deducaoPrevidenciaPrivada;
        this.pensaoAlimenticia = pensaoAlimenticia;
        this.impostoEsperado = impostoEsperado;
    }

    @Before
    public void setUp() {
        irpf = new IRPF();
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            {8000f, 2000f, 1500f, 500f, 1000f, 1500f, 2761.51f}
        });
    }

    @Test
    public void test() {
        // Rendimentos
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, salario);
        irpf.criarRendimento("Aluguel", IRPF.TRIBUTAVEL, aluguel);
        irpf.criarRendimento("Bolsa de estudos", IRPF.NAOTRIBUTAVEL, bolsa);

        // Deduções
        irpf.cadastrarDependente("Filho", "Filho");
        irpf.cadastrarContribuicaoPrevidenciaria(contribuicaoPrevidenciaria);
        irpf.cadastrarDeducaoIntegral("Previdência Privada", deducaoPrevidenciaPrivada);
        irpf.cadastrarPensaoAlimenticia("Filho", pensaoAlimenticia);

        // Calculo do imposto
        assertEquals(impostoEsperado, irpf.calcularImposto(), 0.01f);
    }
}

