/**
 * Enumeração dos status de demanda da fábrica de chocolates.
 * Responsável por exibir a condição da demanda requerida, podendo ser
 * 'PENDENTE', 'PRODUZINDO', 'CONCLUIDA' ou 'CANCELADA' de acordo com
 * o processo de produção e escolha das demandas.
 * 
 * @author Laura Póvoas
 */
public enum StatusDemanda {
    /** Status pendente, demanda não iniciada.*/
    PENDENTE("Pendente", "Demanda cadastrada, mas ainda não iniciada."),
    /** Status produzindo, demanda em processo de fabricação.*/
    PRODUZINDO("Em Produção", "Demanda selecionada e em processo de fabricação."),
    /** Status concluida, demanda com produtos produzidos com sucesso.*/
    CONCLUIDA("Concluída", "Demanda com todos os produtos produzidos com sucesso."),
    /** Status cancelada, demanda cancelada por falta de recursos ou orçamento.*/
    CANCELADA("Cancelada", "Demanda cancelada por falta de orçamento ou insumos.");

    /** Identificador do status.*/
    private final String nome;
    /** Descrição do status da demanda.*/
    private final String descricao;

    /**
     * Construtor do status da demanda.
     *
     * @param nome Identificador do status.
     * @param descrição Descrição do status da demanda.
     */
    StatusDemanda(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    /** Retorna o identificador do status da demanda.*/
    public String getNome() {
        return nome;
    }

    //** Retorna a descrição do status da demanda.*/
    public String getDescricao() {
        return descricao;
    }

}
