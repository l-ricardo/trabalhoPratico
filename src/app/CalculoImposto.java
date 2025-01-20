package app;

public class CalculoImposto {
    private IRPF origem;

    private float baseCalculo;
    private float imposto;

	private static final float LIMITE_FAIXA1 = 2112.01f;
	private static final float LIMITE_FAIXA2 = 2826.65f;
	private static final float LIMITE_FAIXA3 = 3751.05f;
	private static final float LIMITE_FAIXA4 = 4664.68f;

	private static final float ALIQUOTA_FAIXA2 = 0.075f;
	private static final float ALIQUOTA_FAIXA3 = 0.15f;
	private static final float ALIQUOTA_FAIXA4 = 0.225f;
	private static final float ALIQUOTA_FAIXA5 = 0.275f;

    public CalculoImposto(IRPF irpf) {
        this.origem = irpf;
        this.baseCalculo = origem.getBaseCalculoImposto();
        this.imposto = 0.0f;
    }

    public float computar() {
        imposto += origem.getImpostoPorFaixa(baseCalculo, 0.0f, LIMITE_FAIXA1, 0.0f);
        imposto += origem.getImpostoPorFaixa(baseCalculo, LIMITE_FAIXA1, LIMITE_FAIXA2, ALIQUOTA_FAIXA2);
        imposto += origem.getImpostoPorFaixa(baseCalculo, LIMITE_FAIXA2, LIMITE_FAIXA3, ALIQUOTA_FAIXA3);
        imposto += origem.getImpostoPorFaixa(baseCalculo, LIMITE_FAIXA3, LIMITE_FAIXA4, ALIQUOTA_FAIXA4);
        imposto += origem.getImpostoPorFaixa(baseCalculo, LIMITE_FAIXA4, Float.MAX_VALUE, ALIQUOTA_FAIXA5);
        return (float) (Math.floor(imposto * 100.0) / 100.0);
    }    
}
