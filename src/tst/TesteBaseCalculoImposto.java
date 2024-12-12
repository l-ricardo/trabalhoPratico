package tst;

import app.IRPF;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TesteBaseCalculoImposto {

    IRPF irpf;

    private float rendimento1;
    private float rendimento2;
    private float rendimento3;
    private boolean temDeducao;
    private float contribuicaoPrevidenciaria;
    private float deducaoPrevidenciaPrivada;
    private float pensaoAlimenticia;
    private float baseCalculoImpostoRTD; // Base cálculo com deduções
    private float baseCalculoImpostoRT;  // Base cálculo sem deduções

    public TesteBaseCalculoImposto(float rendimento1, float rendimento2, float rendimento3, boolean temDeducao, float contribuicaoPrevidenciaria,
                                   float deducaoPrevidenciaPrivada, float pensaoAlimenticia,
                                   float baseCalculoImpostoRTD, float baseCalculoImpostoRT) {
        this.rendimento1 = rendimento1;
        this.rendimento2 = rendimento2;
        this.rendimento3 = rendimento3;
        this.temDeducao = temDeducao;
        this.contribuicaoPrevidenciaria = contribuicaoPrevidenciaria;
        this.deducaoPrevidenciaPrivada = deducaoPrevidenciaPrivada;
        this.pensaoAlimenticia = pensaoAlimenticia;
        this.baseCalculoImpostoRTD = baseCalculoImpostoRTD;
        this.baseCalculoImpostoRT = baseCalculoImpostoRT;
    }

    @Before
    public void setup() {
        irpf = new IRPF();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                // {rendimento1, rendimento2, rendimento3, contribuicaoPrevidenciaria, deducaoPrevidenciaPrivada, pensaoAlimenticia, baseComDeducoes, baseSemDeducoes}
                {8000f, 2000f, 1500f, true, 500f, 1000f, 1500f, 6810.41f, 10000f}, // Exemplo do enunciado
                {9500f, 4500f, 1000f, false, 0f, 0f, 0f, 14000f, 14000f},          // Apenas rendimentos tributáveis
                {20000f, 1000f, 1200f, true, 600f, 800f, 1500f, 17910.41f, 21000f},  // Rendimentos altos com deduções
        });
    }

    @Test
    public void testeBaseCalculoRendimentoTributavelDeducoes() {
        // Configurar rendimentos
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, rendimento1);
        irpf.criarRendimento("Aluguel", IRPF.TRIBUTAVEL, rendimento2);
        irpf.criarRendimento("Bolsa de estudos", IRPF.NAOTRIBUTAVEL, rendimento3);

        // Configurar deduções
        if (temDeducao) {
            irpf.cadastrarDependente("Filho", "Filho");
            irpf.cadastrarContribuicaoPrevidenciaria(contribuicaoPrevidenciaria);
            irpf.cadastrarDeducaoIntegral("Previdência Privada", deducaoPrevidenciaPrivada);
            irpf.cadastrarPensaoAlimenticia("Filho", pensaoAlimenticia);
        } else {
            irpf.cadastrarContribuicaoPrevidenciaria(contribuicaoPrevidenciaria);
            irpf.cadastrarDeducaoIntegral("Previdência Privada", deducaoPrevidenciaPrivada);
        }

        // Verificar base de cálculo com deduções
        assertEquals(baseCalculoImpostoRTD, irpf.getBaseCalculoImposto(), 0.01f);
    }

    @Test
    public void testeBaseCalculoRendimentoTributavelSemDeducao() {
        // Configurar rendimentos
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, rendimento1);
        irpf.criarRendimento("2° Trabalho", IRPF.TRIBUTAVEL, rendimento2);
        irpf.criarRendimento("Freelancer", IRPF.NAOTRIBUTAVEL, rendimento3);

        // Configurar deduções, onde casos em que há, devemos desconsiderar para este caso
        if (temDeducao) {
            contribuicaoPrevidenciaria = 0;
            deducaoPrevidenciaPrivada = 0;
            pensaoAlimenticia = 0;
            assertEquals(baseCalculoImpostoRT, irpf.getBaseCalculoImposto(), 0.01f);
        } else {
            // Deduções que já estão zeradas, verificar base de cálculo
            assertEquals(baseCalculoImpostoRT, irpf.getBaseCalculoImposto(), 0.01f);
        }


    }
}
