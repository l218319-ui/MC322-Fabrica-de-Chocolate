public abstract class Produto {
    long id;                               // Identificador ́unico do produto;
    String nome;                           // Nome ou tipo do produto;
    boolean status;                        // Estado atual do produto na linha de produção;
    long quantidadeMateriaPrimaNecessaria; // Quantidade de matéria-prima necessária para produzir uma unidade deste produto.
    float qualidade; //Qualidade do produto (0.0 a 1.0);
    float probabilidadeFalhaAcumulada; //Acumula a chance de falha ao passar pelas maquinas;
    static int totalProdutosFabricados = 0; //Atributo estático para contabilizar instÂcias geradas.



    public abstract void processar(); //Define o processamento espec ́ıfico;

    public abstract int calcularTempoProducao(); //Tempo de producão;

    public abstract String getTipo(); //Retorna o tipo do produto.

    public long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public float getQualidade() {
        return qualidade;
    }

    public void setQualidade(float qualidade) {
        this.qualidade = qualidade;
    }

    public long getQuantidadeMateriaPrimaNecessaria() {
        return quantidadeMateriaPrimaNecessaria;
    }
    
    public void aumentarProbabilidadeFalha(float fator){
        this.probabilidadeFalhaAcumulada = this.probabilidadeFalhaAcumulada * fator;
    }
}
