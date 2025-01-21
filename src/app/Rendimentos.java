package app;

public class Rendimentos {
    String[] nomeRendimento;
    boolean[] rendimentoTributavel;
    float[] valorRendimento;
    int numRendimentos;
    float totalRendimentos;

    public Rendimentos() {
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
        //Adicionar o nome do novo rendimento
        String[] temp = new String[nomeRendimento.length + 1];
        for (int i = 0; i < nomeRendimento.length; i++)
            temp[i] = nomeRendimento[i];
        temp[nomeRendimento.length] = nome;
        nomeRendimento = temp;

        //adicionar tributavel ou nao no vetor
        boolean[] temp2 = new boolean[rendimentoTributavel.length + 1];
        for (int i = 0; i < rendimentoTributavel.length; i++)
            temp2[i] = rendimentoTributavel[i];
        temp2[rendimentoTributavel.length] = tributavel;
        rendimentoTributavel = temp2;

        //adicionar valor rendimento ao vetor
        float[] temp3 = new float[valorRendimento.length + 1];
        for (int i = 0; i < valorRendimento.length; i++) {
            temp3[i] = valorRendimento[i];
        }
        temp3[valorRendimento.length] = valor;
        valorRendimento = temp3;

        this.numRendimentos += 1;
        this.totalRendimentos += valor;

    }

    /**
     * Retorna o número de rendimentos já cadastrados para o contribuinte
     *
     * @return numero de rendimentos
     */
    public int getNumRendimentos() {
        return numRendimentos;
    }

    /**
     * Retorna o valor total de rendimentos cadastrados para o contribuinte
     *
     * @return valor total dos rendimentos
     */
    public float getTotalRendimentos() {
        return totalRendimentos;
    }

    /**
     * Retorna o valor total de rendimentos tributáveis do contribuinte
     *
     * @return valor total dos rendimentos tributáveis
     */
    public float getTotalRendimentosTributaveis() {
        float totalRendimentosTributaveis = 0;
        for (int i = 0; i < rendimentoTributavel.length; i++) {
            if (rendimentoTributavel[i]) {
                totalRendimentosTributaveis += valorRendimento[i];
            }
        }
        return totalRendimentosTributaveis;
    }
}