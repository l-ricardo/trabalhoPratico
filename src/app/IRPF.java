package app;

public class IRPF {

	public static final boolean TRIBUTAVEL = true;
	public static final boolean NAOTRIBUTAVEL = false;

	private String[] nomeRendimento;
	private boolean[] rendimentoTributavel;
	private float[] valorRendimento;
	private int numRendimentos;
	private float totalRendimentos;
	
	private String[] nomesDependentes;
	private String[] parentescosDependentes;
	private int numDependentes;

	private final Rendimentos rendimentos = new Rendimentos();
	private final Dependentes dependentes = new Dependentes(this);
	
	private int numContribuicaoPrevidenciaria;
	private float totalContribuicaoPrevidenciaria;
	
	private float totalPensaoAlimenticia;
	
	private String[] nomesDeducoes;
	private float[] valoresDeducoes;

	public IRPF() {
        rendimentos.nomeRendimento = new String[0];
        rendimentos.rendimentoTributavel = new boolean[0];
        rendimentos.valorRendimento = new float[0];

		dependentes.nomesDependentes = new String[0];
		dependentes.parentescosDependentes = new String[0];
		dependentes.numDependentes = 0;
		
		numContribuicaoPrevidenciaria = 0; 
		totalContribuicaoPrevidenciaria = 0f;
		
		totalPensaoAlimenticia = 0f;
		
		nomesDeducoes = new String[0];
		valoresDeducoes = new float[0];
	}

	/**
	 * Cadastra um rendimento na base do contribuinte, informando o nome do
	 * rendimento, seu valor e se ele é tributável ou não.
	 *
	 * @param nome       nome do rendimento a ser cadastrado
	 * @param tributavel true caso seja tributável, false caso contrário
	 * @param valor      valor do rendimento a ser cadastrado
	 */
	public void criarRendimento(String nome, boolean tributavel, float valor) {
		rendimentos.criarRendimento(nome, tributavel, valor);
	}

	/**
	 * Retorna o número de rendimentos já cadastrados para o contribuinte
	 *
	 * @return numero de rendimentos
	 */
	public int getNumRendimentos() {
		return rendimentos.numRendimentos;
	}

	/**
	 * Retorna o valor total de rendimentos cadastrados para o contribuinte
	 *
	 * @return valor total dos rendimentos
	 */
	public float getTotalRendimentos() {
		return rendimentos.totalRendimentos;
	}

	/**
	 * Retorna o valor total de rendimentos tributáveis do contribuinte
	 *
	 * @return valor total dos rendimentos tributáveis
	 */
	public float getTotalRendimentosTributaveis() {

		return rendimentos.getTotalRendimentosTributaveis();
	}

	/**
	 * Método para realizar o cadastro de um dependente, informando seu grau 
	 * de parentesco
	 * @param nome Nome do dependente
	 * @param parentesco Grau de parentesco
	 */
	public void cadastrarDependente(String nome, String parentesco) {
		// adicionar dependente
		dependentes.cadastrarDependente(nome, parentesco);
	}

	/**
	 * Método que retorna o numero de dependentes do contribuinte
	 * @return numero de dependentes
	 */
	public int getNumDependentes() {
		return dependentes.getNumDependentes();
	}
	
	/**
	 * Return o valor do total de deduções para o contribuinte
	 * @return valor total de deducoes
	 */
	public float getDeducao() {
		float total = 0; 
		for (String d: dependentes.nomesDependentes) {
			total += 189.59f;
		}
		total += totalContribuicaoPrevidenciaria;
		
		return total;
	}

	/**
	 * Cadastra um valor de contribuição previdenciária oficial
	 * @param contribuicao valor da contribuição previdenciária oficial
	 */
	public void cadastrarContribuicaoPrevidenciaria(float contribuicao) {
		numContribuicaoPrevidenciaria++;
		totalContribuicaoPrevidenciaria += contribuicao;
	}

	/**
	 * Retorna o numero total de contribuições realizadas como contribuicao 
	 * previdenciaria oficial
	 * @return numero de contribuições realizadas
	 */
	public int getNumContribuicoesPrevidenciarias() {
		return numContribuicaoPrevidenciaria;
	}

	/**
	 * Retorna o valor total de contribuições oficiais realizadas
	 * @return valor total de contribuições oficiais
	 */
	public float getTotalContribuicoesPrevidenciarias() {
		return totalContribuicaoPrevidenciaria;
	}

	/**
	 * Realiza busca do dependente no cadastro do contribuinte
	 * @param nome nome do dependente que está sendo pesquisado
	 * @return nome do dependente ou null, caso nao conste na lista de dependentes
	 */
	public String getDependente(String nome) {
		return dependentes.getDependente(nome);
	}

	/**
	 * Método que retorna o grau de parentesco para um dado dependente, caso ele
	 * conste na lista de dependentes
	 * @param dependente nome do dependente
	 * @return grau de parentesco, nulo caso nao exista o dependente
	 */
	public String getParentesco(String dependente) {
		for (int i = 0; i< dependentes.nomesDependentes.length; i++) {
			if (dependentes.nomesDependentes[i].equalsIgnoreCase(dependente))
				return dependentes.parentescosDependentes[i];
		}
		return null;
	}

	/**
	 * Realiza o cadastro de uma pensao alimenticia para um dos dependentes do 
	 * contribuinte, caso ele seja um filho ou alimentando. 
	 * @param dependente nome do dependente 
	 * @param valor valor da pensao alimenticia
	 */
	public void cadastrarPensaoAlimenticia(String dependente, float valor) {
		String parentesco = getParentesco(dependente); 
		if (parentesco.toLowerCase().contains("filh") || 
			parentesco.toLowerCase().contains("alimentand")) {
			totalPensaoAlimenticia += valor;
		}
	}

	/**
	 * Retorna o valor total pago em pensões alimentícias pelo contribuinte.
	 * @return valor total de pensoes alimenticias
	 */
	public float getTotalPensaoAlimenticia() {
		return totalPensaoAlimenticia;
	}

	/**
	 * Metodo para cadastrar deduções integrais para o contribuinte. Para cada
	 * dedução é informado seu nome e valor. 
	 * @param nome nome da deducao 
	 * @param valorDeducao valor da deducao
	 */
	public void cadastrarDeducaoIntegral(String nome, float valorDeducao) {
		nomesDeducoes = cadastroNomeDeducao(nome);
		valoresDeducoes= cadastroValorDeducao(valorDeducao);
	}

	private String[] cadastroNomeDeducao(String nome) {
		String[] temp = new String[nomesDeducoes.length + 1];
		for (int i=0; i<nomesDeducoes.length; i++) {
			temp[i] = nomesDeducoes[i];
		}
		temp[nomesDeducoes.length] = nome;
		return temp;
	}

	private float[] cadastroValorDeducao(float valorDeducao) {
		float[] temp = new float[valoresDeducoes.length + 1];
		for (int i=0; i<valoresDeducoes.length; i++) {
			temp[i] = valoresDeducoes[i];
		}
		temp[valoresDeducoes.length] = valorDeducao;
		return temp;
	}

	/**
	 * Método para pesquisar uma deducao pelo seu nome. 
	 * @param substring do nome da deducao a ser pesquisada
	 * @return nome da deducao, ou null caso na esteja cadastrada
	 */
	public String getOutrasDeducoes(String nome) {
		for (String d : nomesDeducoes) {
			if (d.toLowerCase().contains(nome.toLowerCase()))
				return d;
		}
		return null;
	}

	/**
	 * Obtem o valor da deducao à partir de seu nome 
	 * @param nome nome da deducao para a qual se busca seu valor
	 * @return valor da deducao
	 */
	public float getDeducao(String nome) {
		for (int i=0; i<nomesDeducoes.length; i++) {
			if (nomesDeducoes[i].toLowerCase().contains(nome.toLowerCase()))
				return valoresDeducoes[i];
		}
		return 0;
	}

	/**
	 * Obtem o valor total de todas as deduções que nao sao do tipo
	 * contribuicoes previdenciarias ou por dependentes
	 * @return valor total das outras deducoes
	 */
	public float getTotalOutrasDeducoes() {
		float soma = 0;
		for (float f : valoresDeducoes) {
			soma += f;
		}
		return soma;
	}

	/**
	* Obtem o valor base para cálculo do imposto a partir dos rendimentos tributáveis e deduções
	* @return valor da base de cálculo para o Imposto, baseado
	* na diferença do total di Rendimento Tributável com o total das Deduções
	*/
	public float getBaseCalculoImposto() {
		float totalTributavel = rendimentos.getTotalRendimentosTributaveis();
		float totalDeducoes = getDeducao() + getTotalPensaoAlimenticia() + getTotalOutrasDeducoes();

		return totalTributavel - totalDeducoes;
	}


    /**
     * Calcula o imposto por faixa.
	 * @param baseCalculo base de cálculo
	 * @param minimo limite inferior da faixa
	 * @param maximo limite superior da faixa
	 * @param aliquota alíquota da faixa
     * @return valor do imposto calculado
     */
    public float getImpostoPorFaixa(float baseCalculo, float minimo, float maximo, float aliquota) {
		if (baseCalculo < minimo) {
			return 0.0f;
		}
		if (baseCalculo >= maximo) {
			baseCalculo = maximo;
		}
		return (float) (Math.floor((baseCalculo - minimo) * aliquota * 100.0) / 100.0);
	}

    /**
     * Calcula o imposto com base na base de cálculo.
     * @return valor do imposto calculado
     */
    public float calcularImposto() {
		return new CalculoImposto(this).computar();
    }

	public float getAliquotaEfetiva() {
		return (calcularImposto() / rendimentos.getTotalRendimentosTributaveis()) * 100;
	}

}
