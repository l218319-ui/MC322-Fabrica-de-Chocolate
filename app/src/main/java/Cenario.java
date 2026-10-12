/**
 * Enumeração dos cenários de simulação disponíveis na fábrica de chocolates.
 * Cada cenário estabelece as diretrizes e regras globais de operação,
 * definindo o orçamento inicial disponível, o nível de desgaste aplicado às máquinas
 * e o fator de probabilidade de falhas do sistema.
 * 
 * @author Laura Póvoas
 */
public enum Cenario {
    /** Cenário ideal, com alto orçamento inicial, desgaste mínimo das máquinas e baixa taxa de falha. */
    IDEAL("IDEAL", 100000.0, 1, 1.0f),
    /** Cenário apocalíptico, com baixo orçamento inicial, elevado desgaste e maior frequência de falhas. */
    APOCALIPTICO("APOCALÍPTICO", 1000.0, 2, 2.0f);
    /** Orçamento inicial atribuído ao cenário. */
    private final double orçamentoInicial;
    /** Fator multiplicador do desgaste de equipamentos. */
    private final int desgaste;
    /** Fator ou índice de falhas do sistema. */
    private final float falha;
    /** Nome descritivo do cenário. */
    private final String nome;
    /**
     * Construtor do cenário de simulação.
     *
     * @param nome Nome de exibição do cenário.
     * @param orçamentoInicial Montante financeiro inicial atribuído à fábrica.
     * @param desgaste Nível/fator de desgaste sofrido pelos equipamentos.
     * @param falha Taxa de falhas atribuída ao cenário.
     */
    private Cenario(String nome, double orçamentoInicial, int desgaste, float falha) {
        this.orçamentoInicial = orçamentoInicial;
        this.desgaste = desgaste;
        this.falha = falha;
        this.nome = nome;
    }

    /** Obtém o valor do orçamento inicial disponível para o cenário.
     *
     * @return O valor do orçamento inicial.
     */
    public double getOrçamentoInicial() {
        return orçamentoInicial;
    }

    /**
     * Obtém a taxa de desgaste aplicável no cenário.
     *
     * @return O fator de desgaste das máquinas.
     */
    public int getDesgaste() {
        return desgaste;
    }

    /**
     * Obtém a taxa de falha associada ao cenário.
     *
     * @return O fator de falha.
     */
    public float getFalha() {
        return falha;
    }

    /**
     * Obtém a denominação do cenário.
     *
     * @return O nome do cenário.
     */
    public String getNome() {
        return nome;
    }
}