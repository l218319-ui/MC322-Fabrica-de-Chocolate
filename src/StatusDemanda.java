public enum StatusDemanda {
    // Constantes:
    PENDENTE("Pendente", "Demanda cadastrada, mas ainda não iniciada."),
    EM_PRODUCAO("Em Produção", "Demanda selecionada e em processo de fabricação."),
    CONCLUIDA("Concluída", "Demanda com todos os produtos produzidos com sucesso."),
    CANCELADA("Cancelada", "Demanda cancelada por falta de orçamento ou insumos.");

    // Atributos:
    private final String nome;
    private final String descricao;

    // Construtor:
    StatusDemanda(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // Métodos: 
    public String getNome() {
        return nome;
    }

    // Retorna uma descrição formatada do status para o menu/relatório:
    public String getDescricao() {
        return descricao;
    }

}
