/**
 * Representa um braço batedor, parte da máquina de processamento (o Todo).
 * Composição estrita com cardinalidade (1:N).
 * 
 * @author Laura Póvoas
 */
public class BracoBatedor implements Auditavel {

    /** Identificador do braço batedor.*/
    private int idBraco;

    /** Velocidade de rotação do braço (RPM).*/
    private double velocidadeRotacao;

    /** Saúde do braço.*/
    private int saude;

    /**
     * Constrói um Braço Batedor.
     *
     * @param idBraco Identificador do braço na máquina.
     * @param velocidadeRotacao Velocidade em RPM.
     */
    public BracoBatedor(int idBraco, double velocidadeRotacao) {
        this.idBraco = idBraco;
        this.velocidadeRotacao = velocidadeRotacao;
        this.saude = 100;
    }

    /** Executa a ação de bater a massa.*/
    public void bater() {
        if (saude <= 0) {
            throw new PlantaException("Braço batedor #" + idBraco + " está quebrado e travou!");
        }
    }

    /**
     * Aplica desgaste ao braço batedor.
     *
     * @param valor Quantidade de desgaste.
     */
    public void aplicarDesgaste(int valor) {
        this.saude = Math.max(0, this.saude - valor);
    }

    /** Restaura a saúde do braço batedor para 100. */
    public void reparar() {
        this.saude = 100;
    }

    /** Retorna o identificador do braço.
     * 
     * @return ID do braço.
     */
    public int getIdBraco() {
        return idBraco;
    }

    /** Retorna a velocidade de rotação do braço.
     * 
     * @return Velocidade de rotação (RPM).
     */
    public double getVelocidadeRotacao() {
        return velocidadeRotacao;
    }

    /** Retorna a saúde atual do braço.
     * 
     * @return Saúde atual do braço.
    */
    public int getSaude() {
        return saude;
    }

    /** Gera relatório de diagnóstico do Braço Batedor, apresentando
     * ID, velocidade, saúde e necessidade de manutenção.
     *
     * @return String formatada com o diagnóstico da Parte.
     */
    @Override
    public String gerarRelatorioDiagnostico() {
        return "[Braço Batedor #" + idBraco + "] Velocidade: " + velocidadeRotacao + " RPM | Saúde: " + saude + "% | Manutenção: " + (precisaManutencao() ? "SIM" : "NÃO");
    }

    /** Avalia se os braços necessitam de reparo.
     *
     * @return {@code true} se a saúde estiver abaixo de 20.
     */   
    @Override
    public boolean precisaManutencao() {
        return saude < 20;
    }
}