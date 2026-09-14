public class Demanda {
    private String tipoProduto; // String com o tipo ou nome do produto demandado;
    private long quantidadeProdutos; // Quantidade de produtos finais;
    private boolean atendida; // Status da demanda.

    public Demanda(String tipoProduto, long quantidadeProdutos, boolean atendida) {
        this.tipoProduto = tipoProduto;
        this.quantidadeProdutos = quantidadeProdutos;
        this.atendida = atendida;
    }

    //Métodos:

    //#misterio
    public void atualizarQuantidade(){

    }

    public long calcularMateriaPrimaNecessaria(Produto produto){
        long MateriaPrimaNecessaria = produto.getQuantidadeMateriaPrimaNecessaria() * this.quantidadeProdutos;
        return MateriaPrimaNecessaria;
    }

    public void atender(){
        this.atendida = true;//??
    }
}
