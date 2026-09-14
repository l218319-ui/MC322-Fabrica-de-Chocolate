public class Maquina {
    private String nome;            // Nome da máquina;
    private boolean ligada;         // Indica se a máquina está ligada;
    private long capacidadeMaxima;  // Capacidade máxima de processamento por ciclo.

    public Maquina(String nome, boolean ligada, long capacidadeMaxima) {
        this.nome = nome;
        this.ligada = false;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    //Métodos:

    // Liga a máquina:
    public void ligar() {
        this.ligada = true;
    }

    // Desliga a máquina:
    public void desligar() {
        this.ligada = false;
    }

    // Transforma a matéria-prima em produto, recebendo como parâmetros a matéria-prima produto e a demanda necessária:
    public void processar(MateriaPrima materiaPrima, Produto produto, long demandaDeProduto) {
        if (!this.estaLigada()) {
            System.out.println("Não é possível processar, pois a máquina está desligada!");
        } else {
            // quantidade de matéria-prima necessária para fabricar a demanda de produto requerida:
            long demandaDeMateriaPrima = produto.getQuantidadeMateriaPrimaNecessaria() * demandaDeProduto;

            if (temEstoqueSuficiente(materiaPrima, produto, demandaDeProduto)) {
                materiaPrima.consumir(demandaDeMateriaPrima);
                produto.processar();
            } else {
                System.out.println("Não é possível processar essa quantidade de produto pois não há matéria-prima suficiente no estoque!");
            }
        }
    }

    // Faz a verificação de estoque:
    public boolean temEstoqueSuficiente(MateriaPrima materiaPrima, Produto produto, long demandaDeProduto) {
        // quantidade de matéria-prima necessária para fabricar a demanda de produto requerida.
            long demandaDeMateriaPrima = produto.getQuantidadeMateriaPrimaNecessaria() * demandaDeProduto;
            if (materiaPrima.verificarDisponibilidade(demandaDeMateriaPrima)) {
                return true;
            } else {
                return false;
            }
    }

    // Retorna o nome da maquina:
    public String getNome() {
        return this.nome;
    }

    // Retorna se a máquina está ligada:
    public boolean estaLigada() {
        return this.ligada;
    }

}
