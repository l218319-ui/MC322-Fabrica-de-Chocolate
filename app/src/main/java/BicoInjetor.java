/**
 * Representa o bico injetor, parte da máquina de processamento (o Todo).
 * Composição (1:1).
 * 
 * @author Laura Póvoas
 */
public class BicoInjetor implements Auditavel {

    /** Vazão de injeção do bico.*/
    private double vazao;

    /** Saúde do bico.*/
    private int saude;

    /** Indica se o bico está entupido. */
    private boolean entupido;

    /**
     * Constrói um bico injetor com a vazão dada.
     *
     * @param vazao Taxa de vazão do bico injetor em L/min.
     */
    public BicoInjetor(double vazao) {
        this.vazao = vazao;
        this.saude = 100;
        this.entupido = false;
    }

    /**
     * Executa o processo de injeção da matéria-prima.
     *
     * @throws PlantaException se o bico estiver entupido.
     */
    public void injetar() {
        if (entupido) {
            throw new PlantaException("O bico injetor está entupido!");
        }
    }

    /**
     * Aplica desgaste ao bico e calcula risco de entupimento.
     *
     * @param valor Quantidade de desgaste.
     */
    public void aplicarDesgaste(int valor) {
        this.saude = Math.max(0, this.saude - valor);
        if (this.saude < 30 && Math.random() < 0.25) {
            this.entupido = true;
        }
    }

    /** Realiza desentupimento e restaura a saúde do bico injetor. */
    public void desentupirEReparar() {
        this.saude = 100;
        this.entupido = false;
    }

    /** Retorna a saúde atual do bico injetor.
     * 
     * @return Saúde atual do bico.
    */
    public int getSaude() {
        return saude;
    }

    /** Retorna a vazão dada do bico injetor.
     * 
     * @return Vazão em L/min.
    */
    public double getVazao() {
        return vazao;
    }

    /** Retorna se o bico está entupido.
     * 
     * @return Entupimento.
     */
    public boolean isEntupido() {
        return entupido;
    }

    /** Gera relatório de diagnóstico do Bico injetor, apresentando
     * Vazão, status de entupimento e necessidade de manutenção.
     *
     * @return String formatada com o diagnóstico da Parte.
     */
    @Override
    public String gerarRelatorioDiagnostico() {
        return "[Bico Injetor] Vazão: " + vazao + " L/min | Saúde: " + saude + "% | Entupido: " + (entupido ? "SIM" : "NÃO") + " | Manutenção: " + (precisaManutencao() ? "SIM" : "NÃO");
    }

    /** Avalia se o bico necessita de reparo.
     *
     * @return {@code true} se o bico estiver entupido ou a saúde estiver abaixo de 20.
     */   
    @Override
    public boolean precisaManutencao() {
        return saude < 20 || entupido;
    }
}