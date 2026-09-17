public class Demanda {

    private Produto tipoProduto; // Nome do produto demandado;
    private long quantidadeProdutos; // Quantidade de produtos;
    private StatusDemanda status; // Status da demanda.

    // Construtor:
    public Demanda(Produto tipoProduto, long quantidadeProdutos) {
        this.tipoProduto = tipoProduto;
        this.quantidadeProdutos = quantidadeProdutos;
        this.status = StatusDemanda.PENDENTE;
    }

    // Métodos:
    public void atualizarQuantidade(long quantidade) {
        this.quantidadeProdutos = quantidade;
    }

    public double calcularMateriaPrimaNecessaria() {
        double MateriaPrimaNecessaria = tipoProduto.getQuantidadeMateriaPrimaNecessaria() * this.quantidadeProdutos;
        return MateriaPrimaNecessaria;
    }

    // Atualiza o status da demanda:
    public void atender() {
        if (this.status != StatusDemanda.CANCELADA) {
            this.status = StatusDemanda.CONCLUIDA;
        }
    }

    // Atualização de status:
    public void setStatus(StatusDemanda novoStatus) {
        if (this.status == StatusDemanda.CANCELADA && novoStatus == StatusDemanda.CONCLUIDA) {
            throw new IllegalStateException("Não é possível concluir uma demanda que está CANCELADA.");
        }
        this.status = novoStatus;
    }

    // Getters q nn vou usar por enquanto, mas deixei aí pra parar com o aviso de
    // The value of the field is not used:
    public Produto getTipoProduto() {
        return tipoProduto;
    }

    public long getQuantidadeProdutos() {
        return quantidadeProdutos;
    }

    public boolean isAtendida() {
        return status == StatusDemanda.CONCLUIDA;
    }

}