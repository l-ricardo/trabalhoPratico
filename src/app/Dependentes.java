package app;

public class Dependentes {
    private final app.IRPF IRPF;
    String[] nomesDependentes;
    String[] parentescosDependentes;
    public int numDependentes;

    public Dependentes(app.IRPF IRPF) {
        this.IRPF = IRPF;
    }

    /**
     * Método para realizar o cadastro de um dependente, informando seu grau
     * de parentesco
     *
     * @param nome       Nome do dependente
     * @param parentesco Grau de parentesco
     */
    public void cadastrarDependente(String nome, String parentesco) {
        // adicionar dependente
        String[] temp = new String[nomesDependentes.length + 1];
        for (int i = 0; i < nomesDependentes.length; i++) {
            temp[i] = nomesDependentes[i];
        }
        temp[nomesDependentes.length] = nome;
        nomesDependentes = temp;

        String[] temp2 = new String[parentescosDependentes.length + 1];
        for (int i = 0; i < parentescosDependentes.length; i++) {
            temp2[i] = parentescosDependentes[i];
        }
        temp2[parentescosDependentes.length] = parentesco;
        parentescosDependentes = temp2;

//        IRPF.setNumDependentes(IRPF.getNumDependentes() + 1);
        numDependentes++;
    }

    /**
     * Método que retorna o numero de dependentes do contribuinte
     *
     * @return numero de dependentes
     */
    public int getNumDependentes() {
        return numDependentes;
    }

    /**
     * Realiza busca do dependente no cadastro do contribuinte
     *
     * @param nome nome do dependente que está sendo pesquisado
     * @return nome do dependente ou null, caso nao conste na lista de dependentes
     */
    public String getDependente(String nome) {
        for (String d : nomesDependentes) {
            if (d.contains(nome))
                return d;
        }
        return null;
    }
}