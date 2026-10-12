/**
 * Máquina responsável por embalar os chocolates produzidos.
 * 
 * @author Laura Póvoas
 */
public class MaquinaEmbaladora extends Maquina {

    /**
     * Constrói a máquina de embalagem.
     *
     * @param nome Nome identificador.
     * @param custoOperacao Custo por ciclo.
     * @param capacidadeMaxima Capacidade operacional.
     */
    public MaquinaEmbaladora(String nome, float custoOperacao, double capacidadeMaxima) {
        super(nome, capacidadeMaxima, 0.0f, custoOperacao);
    }

    /**
     * Obtém o tipo da máquina.
     *
     * @return {@link String} descritiva.
     */
    @Override
    public String getTipo() {
        return "Máquina de embalar chocolates";
    }

    /**
     * Embala o produto recebido, alterando seu status e aplicando desgaste à máquina.
     *
     * @param produto Produto a embalar.
     * @param cenario Cenário ativo.
     * @throws MaquinaIndisponivelException se a máquina estiver desligada ou quebrada.
     * @throws DemandaInvalidaException se o produto for nulo.
     */
    @Override
    public void processar(Produto produto, Cenario cenario) {
        if (produto == null) {
            throw new DemandaInvalidaException("Não é possível embalar um produto nulo.");
        }
        if (!estaLigada()) {
            throw new MaquinaIndisponivelException("Não é possível embalar: a máquina '" + getNome() + "' está desligada!");
        }
        if (this.getSaude() <= 0) {
            throw new MaquinaIndisponivelException("Não é possível embalar: a máquina '" + getNome() + "' está quebrada!");
        }

        produto.setStatus("Produto embalado");
        int dano = random.nextInt(1, 4);
        aplicaDesgaste(dano, cenario);

        double chanceFalha = ((100.0 - this.getSaude()) / 100.0) * (cenario != null ? cenario.getFalha() : 1.0f);
        if (random.nextDouble() < chanceFalha) {
            produto.aumentarProbabilidadeFalha(0.03f);
        }
    }
}