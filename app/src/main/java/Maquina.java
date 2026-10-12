import java.util.Random;

public abstract class Maquina implements Auditavel {
    // Atributos:
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
        return "[Máquina " + this.nome + "], Tipo: " + getTipo() + ", Prob. de Falha: "
                + this.probabilidadeFalha * 100 + "%"
                + ", Saúde da Máquina: " + getSaude()
                + ", Precisa de Manutenção: " + (precisaManutencao() ? "SIM" : "NÃO");
    }

    public boolean precisaManutencao() {
        if (this.probabilidadeFalha >= 0.6f || this.saude < 20) { // verifica prob. falha associada a saúde
            return true;
        } else {
            return false;
        }
    }

    // Métodos Abstratos:
    public abstract void processar(Produto produto, Cenario cenario);

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

    public float getCustoOperacao() {
        return this.custoOperacao;
    }

    // Método protegido para checagem aleatória de falhas:
    protected boolean verificarFalha(Cenario cenario) {
        float fator;
        if (cenario != null){ //incorpora o cenário nas falhas
            fator = cenario.getFalha();
        } else {
            fator = 1.0f;
        }
        if (random.nextDouble() < (probabilidadeFalha * fator)) {
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

    protected void aplicaDesgaste(int valor, Cenario cenario){// Desgaste
        int fatorDes;
         if (cenario != null){ //incorpora o cenário nos desgastes
            fatorDes = cenario.getDesgaste();
        } else {
            fatorDes = 1;
        }
        this.saude -= (valor * fatorDes);
        if (this.saude < 0) { //assegura que a vida não seja negativa
            this.saude = 0;
        }
    }

    public void setSaude(int saude) {
        this.saude = saude;
    }
}
