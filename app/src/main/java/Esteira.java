public class Esteira {

    private String item; // Item atualmente transportado pela esteira;
    private boolean emMovimento; // Indica se a esteira está em funcionamento;
    private long capacidadeMaxima; // Capacidade máxima de peso ou volume que a esteira pode transportar.

    // Construtor:
    public Esteira(String item, long capacidadeMaxima) {
        this.item = item;
        this.emMovimento = false;
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

    // Verifica se está em movimento:
    public boolean estaEmMovimento() {
        return this.emMovimento;
    }

    // Coloca um item na esteira:
    public boolean adicionarItem(String nomeItem, double peso) {
        if (!this.emMovimento) {
            System.out.println("[ERRO] A esteira está desligada!");
            return false;
        }
        if (this.item != null && !this.item.isEmpty()) {
            System.out.println("[ERRO] A esteira já contém um item!");
            return false;
        }

        if (!verificarCapacidade(peso)) {
            System.out.println(
                    "[ERRO] O peso (" + peso + ") excede a capacidade máxima (" + this.capacidadeMaxima + ")!");
            return false;
        }

        this.item = nomeItem;
        System.out.println("[OK] Item '" + nomeItem + "' colocado na esteira.");
        return true;
    }

    // Remove e retorna o item da esteira:
    public String removerItem() {
        if (this.item == null || this.item.isEmpty()) {
            System.out.println("[ERRO] Não há item para ser removido da esteira!");
            return null;
        } else {
            String itemRemovido = this.item;
            this.item = "";
            System.out.println("[OK] Item '" + itemRemovido + "' removido da esteira.");
            return itemRemovido;
        }
    }

    // Verifica se a esteira suporta o peso do item:
    public boolean verificarCapacidade(double peso) {
        return peso <= this.capacidadeMaxima;
    }

    public String getItem() {
        return this.item;
    }

    public double getCapacidadeMaxima() {
        return this.capacidadeMaxima;
    }
}