import java.util.Random;

public abstract class Maquina implements Auditavel {

    private String nome; // Nome da máquina;
    private boolean ligada; // Indica se a máquina está ligada;
    private double capacidadeMaxima; // Capacidade máxima de processamento por ciclo;
    private float probabilidadeFalha; // Chance de falha;
    private float custoOperacao; // Custo por operação;
    protected Random random;
    private int saude; // Desgaste da máquina.

    // Construtor:
    public Maquina(String nome, double capacidadeMaxima, float probabilidadeFalha,
            float custoOperacao) {
        this.nome = nome;
        this.ligada = false;
        this.capacidadeMaxima = capacidadeMaxima;
        this.probabilidadeFalha = probabilidadeFalha;
        this.custoOperacao = custoOperacao;
        this.random = new Random();
        this.saude = 100;
    }

    // Da interface Auditável:

    public String gerarRelatorioDiagnostico() {
        return "- [Máquina " + this.nome + " ] - Tipo: " + getTipo() + " - Prob. de Falha: "
                + this.probabilidadeFalha * 100 +
                " - Saúde da Máquina: " + getSaude();
    }

    public boolean precisaManutencao() {
        if (this.probabilidadeFalha >= 0.2f) { // verifica prob. falha associada a saúde
            return true;
        } else {
            return false;
        }
    }

    // Métodos Abstratos:

    public abstract void processar(Produto produto);

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
        return this.capacidadeMaxima;
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

    public int getSaude() {
        return this.saude;
    }

    public void reparar(){//repara a máq. pra restaurar a saúde em 100
        this.saude = 100;
    }

    protected void aplicaDesgaste(){
        
    }
}
