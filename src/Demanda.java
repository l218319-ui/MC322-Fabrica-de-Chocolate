public class Demanda {

    private Produto tipoProduto; //Nome do produto demandado;
    private long quantidadeProdutos; // Quantidade de produtos;
    private boolean atendida; // Status da demanda.

    // Construtor:
    public Demanda(Produto tipoProduto, long quantidadeProdutos) {
        this.tipoProduto = tipoProduto;
        this.quantidadeProdutos = quantidadeProdutos;
        this.atendida = false;
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
        this.atendida = true;
    }

    // Getters q nn vou usar por enquanto, mas deixei aí pra parar com o aviso de The value of the field is not used:
    public Produto getTipoProduto() {
        return tipoProduto;
    }

    public long getQuantidadeProdutos() {
        return quantidadeProdutos;
    }

    public boolean isAtendida() {
        return atendida;
    }
    
}