public class MateriaPrima {
    private long id; // Identificador ́unico da matéria-prima;
    private String nome; // Nome ou tipo da matéria-prima;
    private double quantidade; // Quantidade disponível em estoque;
    private String unidade; // Unidade de medida;
    private double custoPorLote; // custo em reais de um lote de matérias primas

    // Construtor:
    public MateriaPrima(long id, String nome, long quantidade, String unidade, double custoPorLote) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.custoPorLote = custoPorLote;
    }

    // Métodos:
    public long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public double getQuantidade() {
        return this.quantidade;
    }

    public String getUnidade() {
        return this.unidade;
    }

    public double getCustoPorLote() {
        return custoPorLote;
    }

    // Verifica se há quantidade suficiente para atender uma demanda específica:
    public boolean verificarDisponibilidade(double demanda) {
        boolean temEstoque = false;
        if (this.quantidade < demanda) {
            System.out.println("[ERRO]  Não há quantidade suficiente no estoque!");
        } else {
            temEstoque = true;
        }
        return temEstoque;
    }

    // Reduz a quantidade disponível da matéria-prima, recebendo como parâmetro a
    // quantidade demandada:
    public void consumir(double demanda) {
        if (verificarDisponibilidade(demanda)) {
            this.quantidade -= demanda;
        }
    }

    // Adiciona quantidade ao estoque de matéria-prima:
    public void adicionarEstoque(double remessa) {
        if (remessa <= 0) {
            System.out.println("[ERRO]  Não é possível adicionar uma quantidade menor ou igual a zero no estoque!");
        } else {
            this.quantidade += remessa;
        }
    }
}
