/**
 * Máquina de vigilância e inspeção de qualidade dos produtos acabados.
 * 
 * @author Laura Póvoas
 */
public class MaquinaInspecaoQualidade extends Maquina {

    /**
     * Constrói a máquina de inspeção com probabilidade base de falha em 4%.
     *
     * @param nome Nome do equipamento.
     * @param custoOperacao Custo financeiro por inspeção.
     */
    public MaquinaInspecaoQualidade(String nome, float custoOperacao) {
        super(nome, 100.0, 0.04f, custoOperacao);
    }

    /**
     * Obtém o tipo da máquina.
     *
     * @return {@link String} descritiva.
     */
    @Override
    public String getTipo() {
        return "Máquina de inspeção de qualidade";
    }

    /**
     * Avalia a qualidade do produto e o aprova ou rejeita de acordo com as falhas acumuladas e condições da máquina.
     *
     * @param produto Produto sob verificação.
     * @param cenario Cenário ativo.
     * @throws MaquinaIndisponivelException se a máquina estiver desligada ou quebrada.
     * @throws DemandaInvalidaException se o produto for nulo.
     */
    @Override
    public void processar(Produto produto, Cenario cenario) {
        if (produto == null) {
            throw new DemandaInvalidaException("Não é possível inspecionar um produto nulo.");
        }
        if (!estaLigada()) {
            throw new MaquinaIndisponivelException("Não é possível inspecionar: a máquina '" + getNome() + "' está desligada!");
        }
        if (this.getSaude() <= 0) {
            throw new MaquinaIndisponivelException("Não é possível inspecionar: a máquina '" + getNome() + "' está quebrada!");
        }

        double chanceFalha = ((100.0 - this.getSaude()) / 100.0) * (cenario != null ? cenario.getFalha() : 1.0f);
        float defeitoBase = produto.getQualidade() * 0.01f;
        float chanceDefeitoTotal = defeitoBase + produto.getProbabilidadeFalhaAcumulada();

        if (verificarFalha(cenario)) {
            produto.setStatus("REJEITADO");
            System.out.println("[ALERTA] Produto REJEITADO! (Falha na Máquina de Inspeção)");
        } else if (this.getSaude() < 20 && random.nextDouble() < chanceFalha) {
            produto.setStatus("REJEITADO");
            System.out.println("[ALERTA] Produto REJEITADO (A saúde da máquina está baixa, providencie reparos!)");
        } else if (random.nextDouble() < chanceDefeitoTotal) {
            produto.setStatus("REJEITADO");
            System.out.println("[ALERTA] Produto REJEITADO na inspeção de qualidade!");
        } else {
            produto.setStatus("APROVADO - INSPEÇÃO");
        }

        int dano = random.nextInt(1, 4);
        aplicaDesgaste(dano, cenario);
    }
}