import java.util.Random;

/**
 * Classe abstrata que representa a estrutura e comportamento base de uma máquina da fábrica.
 * Implementa {@link Auditavel} para permitir diagnósticos e relatórios de saúde e falhas.
 * 
 * @author Laura Póvoas
 */
public abstract class Maquina implements Auditavel {

    /** Nome identificador da máquina. */
    private String nome;

    /** Indica se a máquina está ligada. */
    private boolean ligada;

    /** Capacidade máxima de processamento. */
    private double capacidadeMaxima;

    /** Probabilidade base de ocorrência de falhas. */
    private float probabilidadeFalha;

    /** Custo financeiro por operação realizada. */
    private float custoOperacao;

    /** Gerador de números aleatórios para simulação de eventos e desgastes. */
    protected Random random;

    /** Saúde atual/nível de integridade física da máquina (0 a 100). */
    private int saude;

    /**
     * Instancia uma nova máquina configurando suas especificações.
     *
     * @param nome Nome da máquina.
     * @param capacidadeMaxima Capacidade limite.
     * @param probabilidadeFalha Probabilidade inicial de falha.
     * @param custoOperacao Custo em Reais por ciclo executado.
     * @throws PlantaException se a capacidade máxima for menor ou igual a zero.
     */
    public Maquina(String nome, double capacidadeMaxima, float probabilidadeFalha, float custoOperacao) {
        if (capacidadeMaxima <= 0) {
            throw new PlantaException("A capacidade máxima da máquina deve ser superior a zero.");
        }
        this.nome = nome;
        this.ligada = false;
        this.capacidadeMaxima = capacidadeMaxima;
        this.probabilidadeFalha = probabilidadeFalha;
        this.custoOperacao = custoOperacao;
        this.random = new Random();
        this.saude = 100;
    }

    /**
     * Gera o relatório de diagnóstico do equipamento.
     *
     * @return {@link String} detalhando tipo, taxa de falha e saúde.
     */
    @Override
    public String gerarRelatorioDiagnostico() {
        return "[Máquina " + this.nome + "], Tipo: " + getTipo() + ", Prob. de Falha: "
                + (this.probabilidadeFalha * 100) + "%"
                + ", Saúde da Máquina: " + getSaude()
                + ", Precisa de Manutenção: " + (precisaManutencao() ? "SIM" : "NÃO");
    }

    /**
     * Avalia se a máquina necessita de reparo preventivo ou corretivo.
     *
     * @return {@code true} se a probabilidade de falha for elevada ou a saúde estiver abaixo de 20.
     */
    @Override
    public boolean precisaManutencao() {
        return this.probabilidadeFalha >= 0.6f || this.saude < 20;
    }

    /**
     * Processa a etapa fabril em um determinado produto sob as regras de um cenário.
     *
     * @param produto Produto sendo processado.
     * @param cenario Cenário de operação ativo.
     * @throws MaquinaIndisponivelException se a máquina estiver desligada ou quebrada.
     */
    public abstract void processar(Produto produto, Cenario cenario);

    /**
     * Retorna a descrição do tipo da máquina.
     *
     * @return {@link String} com a categoria do equipamento.
     */
    public abstract String getTipo();

    /** Liga a máquina. */
    public void ligar() {
        this.ligada = true;
    }

    /** Desliga a máquina. */
    public void desligar() {
        this.ligada = false;
    }

    /**
     * Verifica se o equipamento está ligado.
     *
     * @return {@code true} se ligada; {@code false} se desligada.
     */
    public boolean estaLigada() {
        return this.ligada;
    }

    /**
     * Obtém o nome da máquina.
     *
     * @return O nome cadastrado.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Obtém a capacidade máxima.
     *
     * @return A capacidade.
     */
    public double getCapacidadeMaxima() {
        return this.capacidadeMaxima;
    }

    /**
     * Obtém o custo de operação.
     *
     * @return Custo por ciclo.
     */
    public float getCustoOperacao() {
        return this.custoOperacao;
    }

    /**
     * Checa aleatoriamente se ocorreu uma falha, considerando a taxa do cenário.
     *
     * @param cenario Cenário ativo.
     * @return {@code true} se uma falha ocorreu.
     */
    protected boolean verificarFalha(Cenario cenario) {
        float fator = (cenario != null) ? cenario.getFalha() : 1.0f;
        return random.nextDouble() < (probabilidadeFalha * fator);
    }

    /**
     * Obtém o índice de saúde da máquina.
     *
     * @return Valor inteiro entre 0 e 100.
     */
    public int getSaude() {
        return this.saude;
    }

    /** Restaura o nível de saúde da máquina para 100. */
    public void reparar() {
        this.saude = 100;
    }

    /**
     * Aplica um nível de desgaste/dano à saúde da máquina de acordo com o cenário.
     *
     * @param valor Quantidade de desgaste a ser subtraída.
     * @param cenario Cenário ativo.
     */
    protected void aplicaDesgaste(int valor, Cenario cenario) {
        int fatorDes = (cenario != null) ? cenario.getDesgaste() : 1;
        this.saude -= (valor * fatorDes);
        if (this.saude < 0) {
            this.saude = 0;
        }
    }

    /**
     * Define o valor numérico da saúde da máquina.
     *
     * @param saude Novo nível de saúde.
     */
    public void setSaude(int saude) {
        this.saude = saude;
    }
}