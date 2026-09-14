public abstract class Produto {

    private long id; // Identificador ́unico do produto;
    private String nome; // Nome ou tipo do produto;
    private String status; // Estado atual do produto na linha de produção;
    private double quantidadeMateriaPrimaNecessaria; // Quantidade de matéria-prima necessária para produzir uma unidade
    private float qualidade; // Qualidade do produto (0.0 a 1.0);
    private float probabilidadeFalhaAcumulada; // Acumula a chance de falha ao passar pelas maquinas;
    private static int totalProdutosFabricados = 0;// Atributo estático para contabilizar instÂcias geradas.

    // Construtor:
    public Produto(int id, String nome, double quantidadeMateriaPrimaNecessaria, float qualidade) {
        this.id = id;
        this.nome = nome;
        this.status = "Não processado";
        this.quantidadeMateriaPrimaNecessaria = quantidadeMateriaPrimaNecessaria;
        this.qualidade = qualidade;
        this.probabilidadeFalhaAcumulada = 0.0f;
        totalProdutosFabricados++;
    }

    // Métodos Abstratos:
    public abstract void processar();

    public abstract float calcularTempoProducao();

    public abstract String getTipo();

    // Métodos Concretos:
    public long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getQualidade() {
        return qualidade;
    }

    public double getQuantidadeMateriaPrimaNecessaria() {
        return quantidadeMateriaPrimaNecessaria;
    }

    public void aumentarProbabilidadeFalha(float fator) {
        this.probabilidadeFalhaAcumulada += fator;
    }

    public float getProbabilidadeFalhaAcumulada() {
        return probabilidadeFalhaAcumulada;
    }

    public static int getTotalProdutosFabricados() {
        return totalProdutosFabricados;
    }

}
