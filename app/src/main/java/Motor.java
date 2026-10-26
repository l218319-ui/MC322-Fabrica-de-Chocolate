/**
 * Representa o motor, parte da máquina de processamento (o Todo).
 * Composição estrita (1:1).
 * 
 * @author Laura Póvoas
 */
public class Motor implements Auditavel {

    /** Potência do motor (CV). */
    private double potencia;

    /** Saúde do motor. */
    private int saude;

    /** Status de operação do motor. */
    private boolean emFuncionamento;

    /** Constrói um Motor com a potência dada.
     *
     * @param potencia Potência do motor.
     */
    public Motor(double potencia) {
        this.potencia = potencia;
        this.saude = 100;
        this.emFuncionamento = false;
    }

    /** Aciona o motor para operação.*/
    public void acionar() {
        this.emFuncionamento = true;
    }

    /** Desliga o motor.*/
    public void desligar() {
        this.emFuncionamento = false;
    }

    /**
     * Aplica desgaste à saúde do motor.
     *
     * @param valor Quantidade de desgaste.
     */
    public void aplicarDesgaste(int valor) {
        this.saude = Math.max(0, this.saude - valor);
    }

    /** Restaura a saúde do motor para 100%. */
    public void reparar() {
        this.saude = 100;
    }

     /** Retorna a saúde atual do motor.
     * 
     * @return Saúde atual do motor.
    */
    public int getSaude() {
        return saude;
    }

    /** Retorna a potência do motor.
     * 
     * @return Potência atual do motor.
    */
    public double getPotencia() {
        return potencia;
    }

    /** Avalia se o motor está em funcionamento.
     *
     * @return {@code true} se o motor estiver em funcionamento ou {@code false} se o motor não estiver em funcionamento.
     */  
    public boolean isEmFuncionamento() {
        return emFuncionamento;
    }

    /** Gera relatório de diagnóstico do motor, apresentando
     * potência, saúde, status de funcionamento e necessidade de manutenção.
     *
     * @return String formatada com o diagnóstico da Parte.
     */
    @Override
    public String gerarRelatorioDiagnostico() {
        return "[Motor] Potência: " + potencia + " CV | Saúde: " + saude + "% | Status: " + (emFuncionamento ? "EM OPERAÇÃO" : "DESLIGADO") + " | Manutenção: " + (precisaManutencao() ? "SIM" : "NÃO");
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