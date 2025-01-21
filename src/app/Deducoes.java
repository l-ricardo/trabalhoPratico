package app;

public class Deducoes {
    int numContribuicaoPrevidenciaria;
    float totalContribuicaoPrevidenciaria;
    float totalPensaoAlimenticia;
    String[] nomesDeducoes;
    float[] valoresDeducoes;
    private IRPF irpf;


    public Deducoes(IRPF irpf) {
        this.irpf = irpf;
    }

    /**
     * Return o valor do total de deduções para o contribuinte
     *
     * @return valor total de deducoes
     */
    public float getDeducao() {
        float total = 0;
        for (String d : irpf.getNomesDependentes()) {
            total += 189.59f;
        }
        total += totalContribuicaoPrevidenciaria;

        return total;
    }

    /**
     * Cadastra um valor de contribuição previdenciária oficial
     *
     * @param contribuicao valor da contribuição previdenciária oficial
     */
    public void cadastrarContribuicaoPrevidenciaria(float contribuicao) {
        numContribuicaoPrevidenciaria++;
        totalContribuicaoPrevidenciaria += contribuicao;
    }

    /**
     * Retorna o numero total de contribuições realizadas como contribuicao
     * previdenciaria oficial
     *
     * @return numero de contribuições realizadas
     */
    public int getNumContribuicoesPrevidenciarias() {
        return numContribuicaoPrevidenciaria;
    }

    /**
     * Retorna o valor total de contribuições oficiais realizadas
     *
     * @return valor total de contribuições oficiais
     */
    public float getTotalContribuicoesPrevidenciarias() {
        return totalContribuicaoPrevidenciaria;
    }

    /**
     * Realiza o cadastro de uma pensao alimenticia para um dos dependentes do
     * contribuinte, caso ele seja um filho ou alimentando.
     *
     * @param dependente nome do dependente
     * @param valor      valor da pensao alimenticia
     */
    public void cadastrarPensaoAlimenticia(String dependente, float valor) {
        String parentesco = irpf.getParentesco(dependente);
        if (parentesco.toLowerCase().contains("filh") ||
                parentesco.toLowerCase().contains("alimentand")) {
            totalPensaoAlimenticia += valor;
        }
    }

    /**
     * Retorna o valor total pago em pensões alimentícias pelo contribuinte.
     *
     * @return valor total de pensoes alimenticias
     */
    public float getTotalPensaoAlimenticia() {
        return totalPensaoAlimenticia;
    }

    /**
     * Metodo para cadastrar deduções integrais para o contribuinte. Para cada
     * dedução é informado seu nome e valor.
     *
     * @param nome         nome da deducao
     * @param valorDeducao valor da deducao
     */
    public void cadastrarDeducaoIntegral(String nome, float valorDeducao) {
        nomesDeducoes = cadastroNomeDeducao(nome);
        valoresDeducoes = cadastroValorDeducao(valorDeducao);
    }

    String[] cadastroNomeDeducao(String nome) {
        String[] temp = new String[nomesDeducoes.length + 1];
        for (int i = 0; i < nomesDeducoes.length; i++) {
            temp[i] = nomesDeducoes[i];
        }
        temp[nomesDeducoes.length] = nome;
        return temp;
    }

    float[] cadastroValorDeducao(float valorDeducao) {
        float[] temp = new float[valoresDeducoes.length + 1];
        for (int i = 0; i < valoresDeducoes.length; i++) {
            temp[i] = valoresDeducoes[i];
        }
        temp[valoresDeducoes.length] = valorDeducao;
        return temp;
    }

    /**
     * Método para pesquisar uma deducao pelo seu nome.
     *
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
     *
     * @param nome nome da deducao para a qual se busca seu valor
     * @return valor da deducao
     */
    public float getDeducao(String nome) {
        for (int i = 0; i < nomesDeducoes.length; i++) {
            if (nomesDeducoes[i].toLowerCase().contains(nome.toLowerCase()))
                return valoresDeducoes[i];
        }
        return 0;
    }

    /**
     * Obtem o valor total de todas as deduções que nao sao do tipo
     * contribuicoes previdenciarias ou por dependentes
     *
     * @return valor total das outras deducoes
     */
    public float getTotalOutrasDeducoes() {
        float soma = 0;
        for (float f : valoresDeducoes) {
            soma += f;
        }
        return soma;
    }
}