public class MateriaPrima {
    private long id;               // Identificador ́unico da matéria-prima;
    private String nome;           // Nome ou tipo da matéria-prima;
    private long quantidade;       // Quantidade disponível em estoque;
    private String unidade;        // Unidade de medida;
    private long quantidadeMinima; // Quantidade mínima aceitável para produção.

    public MateriaPrima(long id, String nome, long quantidade, String unidade, long quantidadeMinima) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.quantidadeMinima = quantidadeMinima;
    }

    //Métodos:

    // Getters:
    public long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public long getQuantidade() {
        return this.quantidade;
    }  

    public String getUnidade() {
        return this.unidade;
    }

    // Reduz a quantidade disponível da matéria-prima, recebendo como parâmetro a quantidade demandada:
    public void consumir(long quantidade) {
        if (verificarDisponibilidade(quantidade)) {
            this.quantidade = this.quantidade - quantidade;
        }
    }

    // Adiciona quantidade ao estoque de matéria-prima:
    public void adicionarEstoque(long quantidade) {
        if (quantidade <= 0) {
            System.out.println("Não é possível adicionar uma quantidade menor ou igual a zero no estoque!");
        } else {
            this.quantidade = this.quantidade + quantidade;
        }
    }

    // Verifica se há quantidade suficiente para atender uma demanda específica:
    public boolean verificarDisponibilidade(long quantidade) {
        boolean temEstoque = false;
        if (this.quantidade < quantidade) {
            System.out.println("Não há quantidade suficiente no estoque!");
        } else {
            temEstoque = true;
        }
        return temEstoque;
    }
}
