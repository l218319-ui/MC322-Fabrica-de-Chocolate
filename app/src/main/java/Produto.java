/**
 * Classe abstrata que serve de molde para todos os produtos fabricados.
 * Implementa a interface {@link Auditavel} para geração de diagnósticos e indicadores de manutenção.
 * 
 * @author Laura Póvoas
 */
public abstract class Produto implements Auditavel {

    /** Identificador único do produto. */
    private long id;

    /** Nome comercial. */
    private String nome;

    /** Status corrente do produto na esteira/linha. */
    private String status;

    /** Quantidade de matéria-prima consumida por unidade. */
    private double quantidadeMateriaPrimaNecessaria;

    /** Índice de qualidade do produto. */
    private float qualidade;

    /** Chance de falha acumulada ao passar pelas máquinas. */
    private float probabilidadeFalhaAcumulada;

    /** Contabilizador estático total de instâncias fabricadas. */
    private static int totalProdutosFabricados = 0;

    /** Código do lote ao qual o item pertence. */
    private String lote;

    /**
     * Construtor base para produtos.
     *
     * @param id Identificador do produto.
     * @param nome Nome comercial.
     * @param quantidadeMateriaPrimaNecessaria Quantidade necessária de insumos.
     * @param qualidade Nota de qualidade do produto.
     * @param lote Código do lote.
     */
    public Produto(int id, String nome, double quantidadeMateriaPrimaNecessaria, float qualidade, String lote) {
        this.id = id;
        this.nome = nome;
        this.status = "Não processado";
        this.quantidadeMateriaPrimaNecessaria = quantidadeMateriaPrimaNecessaria;
        this.qualidade = qualidade;
        this.probabilidadeFalhaAcumulada = 0.0f;
        totalProdutosFabricados++;
        this.lote = lote;
    }

    /**
     * Gera relatório de auditoria do produto.
     *
     * @return {@link String} formatada com os dados do produto.
     */
    @Override
    public String gerarRelatorioDiagnostico() {
        return "- [Produto " + this.nome + " ], ID: " + this.id + ", Qualidade: " + this.qualidade + 
        ", Prob. de Falha Acumulada: " + (this.probabilidadeFalhaAcumulada * 100) + "%" +
        ",  Status: " + this.status;
    }

    /**
     * Informa se o produto apresenta problemas que demandam verificação.
     *
     * @return {@code true} se a chance de falha acumulada for alta ou a qualidade baixa.
     */
    @Override
    public boolean precisaManutencao() {
        return this.probabilidadeFalhaAcumulada >= 0.3f || this.qualidade < 0.7f;
    }

    /**
     * Clona a instância do produto.
     *
     * @return Novo objeto idêntico de {@link Produto}.
     */
    public abstract Produto clonar();

    /** Inicia o processamento específico do tipo de produto. */
    public abstract void processar();

    /**
     * Calcula o tempo de fabricação do produto.
     *
     * @return Tempo em minutos.
     */
    public abstract float calcularTempoProducao();

    /**
     * Obtém a categoria descritiva do produto.
     *
     * @return {@link String} com o tipo.
     */
    public abstract String getTipo();

    /**
     * Obtém o ID.
     *
     * @return O id.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Obtém o nome.
     *
     * @return O nome.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Obtém o status.
     *
     * @return O status.
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Atualiza o status do produto.
     *
     * @param status Novo status.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Obtém a qualidade.
     *
     * @return A qualidade.
     */
    public float getQualidade() {
        return qualidade;
    }

    /**
     * Obtém a quantidade necessária de insumo.
     *
     * @return Quantidade de matéria-prima.
     */
    public double getQuantidadeMateriaPrimaNecessaria() {
        return quantidadeMateriaPrimaNecessaria;
    }

    /**
     * Eleva a probabilidade acumulada de falha do produto.
     *
     * @param fator Valor do incremento.
     */
    public void aumentarProbabilidadeFalha(float fator) {
        this.probabilidadeFalhaAcumulada += fator;
    }

    /**
     * Obtém a taxa de falha acumulada.
     *
     * @return Valor da probabilidade acumulada.
     */
    public float getProbabilidadeFalhaAcumulada() {
        return probabilidadeFalhaAcumulada;
    }

    /**
     * Retorna o total acumulado de instâncias fabricadas.
     *
     * @return O total de produtos.
     */
    public static int getTotalProdutosFabricados() {
        return totalProdutosFabricados;
    }

    /**
     * Obtém o código do lote.
     *
     * @return O lote.
     */
    public String getLote() {
        return lote;
    }

    /** Reseta a probabilidade de falha acumulada para 0. */
    public void resetarProbabilidadeFalha() {
        this.probabilidadeFalhaAcumulada = 0.0f;
    }
}