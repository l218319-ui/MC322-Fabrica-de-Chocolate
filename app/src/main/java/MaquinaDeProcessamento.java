/**
 * Máquina dedicada ao processamento e transformação da matéria-prima em chocolate.
 * 
 * @author Laura Póvoas
 */
public class MaquinaDeProcessamento extends Maquina {

    /**
     * Constrói a máquina de processamento.
     *
     * @param nome Nome do equipamento.
     * @param custoOperacao Custo monetário por operação.
     * @param capacidadeMaxima Capacidade produtiva por ciclo.
     */
    public MaquinaDeProcessamento(String nome, float custoOperacao, double capacidadeMaxima) {
        super(nome, capacidadeMaxima, 0.0f, custoOperacao);
    }

    /**
     * Obtém o tipo do equipamento.
     *
     * @return {@link String} com a categoria da máquina.
     */
    @Override
    public String getTipo() {
        return "Máquina de processamento";
    }

    /**
     * Realiza o processamento do produto na linha, aplicando desgaste e atualizando probabilidade de falha.
     *
     * @param produto Produto a ser processado.
     * @param cenario Cenário de operação.
     * @throws MaquinaIndisponivelException se a máquina estiver desligada ou com saúde zero (quebrada).
     * @throws DemandaInvalidaException se o produto for nulo.
     */
    @Override
    public void processar(Produto produto, Cenario cenario) {
        if (produto == null) {
            throw new DemandaInvalidaException("Não é possível processar um produto nulo.");
        }
        if (!this.estaLigada()) {
            throw new MaquinaIndisponivelException("Não é possível processar: a máquina '" + getNome() + "' está desligada!");
        }
        if (this.getSaude() <= 0) {
            throw new MaquinaIndisponivelException("Não é possível processar: a máquina '" + getNome() + "' está quebrada!");
        }

        produto.processar();
        int dano = random.nextInt(1, 4);
        aplicaDesgaste(dano, cenario);

        double chanceFalha = ((100.0 - this.getSaude()) / 100.0) * (cenario != null ? cenario.getFalha() : 1.0f);
        if (random.nextDouble() < chanceFalha) {
            produto.aumentarProbabilidadeFalha(0.05f);
        }
    }
}