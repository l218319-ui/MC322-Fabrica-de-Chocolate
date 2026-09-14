public class Esteira {
    private String item; // Item atualmente transportado pela esteira;
    private boolean emMovimento; // Indica se a esteira está em funcionamento;
    private long capacidadeMaxima; // Capacidade máxima de peso ou volume que a esteira pode transportar.

    // Construtor:
    public Esteira(String item, boolean emMovimento, long capacidadeMaxima) {
        this.item = item;
        this.emMovimento = emMovimento;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    // Métodos:

    // Coloca a esteira em funcionamento:
    public void ligar() {
        this.emMovimento = true;
        System.out.println("[OK] Esteira ligada...");
    }

    // Desliga a esteira:
    public void desligar() {
        this.emMovimento = false;
        System.out.println("[OK] Esteira desligada.");
    }

    // Coloca um item na esteira:
    public boolean adicionarItem(String nome, double demandaDeProduto, Produto produto) {
        double demandaDeMateriaPrima = produto.getQuantidadeMateriaPrimaNecessaria() * demandaDeProduto;
        if (!this.emMovimento) {
            System.out.println("[ERRO]  A esteira está parada. Ligue antes de adicionar um item!");
            return false;
        }
        if (this.item != null && !this.item.isEmpty()) {
            System.out.println("[ERRO]  A esteira já contém um item e não aceita outro!");
            return false;
        }

        if (!verificarCapacidade(demandaDeMateriaPrima)) {
            System.out.println("O peso (" + demandaDeMateriaPrima + ") excede a capacidade máxima (" + this.capacidadeMaxima + ")!");
            return false;
        }

        this.item = nome;
        System.out.println("[OK] Item '" + nome + "' colocado na esteira.");
        return true;
    }

    // Remove e retorna o item da esteira:
    public String removerItem() {
        if (this.item == null || this.item.isEmpty()) {
            System.out.println("[ERRO]  Não há item para ser removido da esteira!");
            return null;
        } else {
            String itemRemovido = this.item;
            this.item = "";
            return itemRemovido;
        }
    }

    // Verifica se a esteira suporta o peso/volume do item:
    public boolean verificarCapacidade(double peso) {
        return peso <= this.capacidadeMaxima;
    }
}