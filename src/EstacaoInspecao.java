public class EstacaoInspecao {
    private boolean ativa; // Indica se a estação está ativa;
    private long produtosInspecionados; // Contador de produtos inspecionados.

    public EstacaoInspecao(boolean ativa, long produtosInspecionados) {
        this.ativa = false;
        this.produtosInspecionados = produtosInspecionados;
    }

    // Métodos:

    // Ativa a estação:
    public void ativar() {
        this.ativa = true;
        System.out.println("[OK] Estação de inspeção ativada.");
    }

    // Desativa a estação:
    public void desativar() {
        this.ativa = false;
    }

    // Realiza a inspeção de um produto;
    public void inspecionar() {
        this.produtosInspecionados = this.produtosInspecionados + 1;
    }

    public long getTotalInspecionados() {
        return produtosInspecionados;
    }
}
