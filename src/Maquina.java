import java.util.Random;

public abstract class Maquina {

    private String nome; // Nome da máquina;
    private boolean ligada; // Indica se a máquina está ligada;
    private double capacidadeMaxima; // Capacidade máxima de processamento por ciclo;
    private float probabilidadeFalha; // Chance de falha;
    private float custoOperacao; // Custo por operação.
    protected Random random;

    // Construtor:
    public Maquina(String nome, double capacidadeMaxima, float probabilidadeFalha,
            float custoOperacao) {
        this.nome = nome;
        this.ligada = false;
        this.capacidadeMaxima = capacidadeMaxima;
        this.probabilidadeFalha = probabilidadeFalha;
        this.custoOperacao = custoOperacao;
        this.random = new Random();
    }

    // Métodos Abstratos:
    public abstract void processar(Produto produto);// ainda nn sei oq colocar dentro disso aqui

    public abstract String getTipo();// Retorna o tipo da máquina.

    // Métodos Concretos:
    // Liga a máquina:
    public void ligar() {
        this.ligada = true;
    }

    // Desliga a máquina:
    public void desligar() {
        this.ligada = false;
    }

    public boolean estaLigada() {
        return this.ligada;
    }

    public String getNome() {
        return this.nome;
    }

    public double getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public float getCustoOperacao() { // custo com energia e manutenção?
        return this.custoOperacao;
    }

    // Método protegido para checagem aleatória de falhas:
    protected boolean verificarFalha() {
        if (random.nextDouble() < probabilidadeFalha) {
            return true;
        }
        return false;
    }
}
