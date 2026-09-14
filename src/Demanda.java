public class Demanda {

    private String tipoProduto; // String com o tipo ou nome do produto demandado;
    private long quantidadeProdutos; // Quantidade de produtos;
    private boolean atendida; // Status da demanda.

    // Construtor:
    public Demanda(String tipoProduto, long quantidadeProdutos) {
        this.tipoProduto = tipoProduto;
        this.quantidadeProdutos = quantidadeProdutos;
        this.atendida = false;
    }

    // Métodos:
    public void atualizarQuantidade(long quantidade) {
        this.quantidadeProdutos = quantidade;
    }

    public double calcularMateriaPrimaNecessaria(Produto produto) {
        double MateriaPrimaNecessaria = produto.getQuantidadeMateriaPrimaNecessaria() * this.quantidadeProdutos;
        return MateriaPrimaNecessaria;
    }

    // Atualiza o status da demanda:
    public void atender() {
        this.atendida = true;
    }

    // Getters q nn vou usar por enquanto, mas deixei aí pra parar com o aviso de The value of the field is not used:
    public String getTipoProduto() {
        return tipoProduto;
    }

    public long getQuantidadeProdutos() {
        return quantidadeProdutos;
    }

    public boolean isAtendida() {
        return atendida;
    }
    
}