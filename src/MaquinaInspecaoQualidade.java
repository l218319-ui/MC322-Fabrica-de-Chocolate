public class MaquinaInspecaoQualidade extends Maquina {

    // Construtor:
    public MaquinaInspecaoQualidade(String nome, float custoOperacao) {
        super(nome, 100.0, 0.2f, custoOperacao);
    }

    public String getTipo() {
        return "Máquina de inspeção de qualidade";
    }

    public void processar(Produto produto) {
        if (!estaLigada()) {
            System.out.println("[ERRO]  Não é possível inspecionar, pois a máquina " + getNome() + "está desligada!");
        } else if (this.getSaude() <= 0) {
            System.out.println("[ERRO]  Não é possível inspecionar, pois a máquina " + getNome() + " está quebrada!");
        } else {
            if (verificarFalha()) {// checagem de falha da maquina
                produto.setStatus("REJEITADO");
                System.out.println("[ERRO]  Produto REJEITADO! (Falha na Máquina de Inspeção)");
            }
            //prob. de falha da maq. de inspeção:
            float rejeicao = (produto.getQualidade() * 0.1f) + produto.getProbabilidadeFalhaAcumulada();
            /*pega a qualidade e multiplica por um fator de 0.1 (se nn for assim o produto de alta qualidade tem 90% de
            chance de falha) aí a chance de falha fica proporcional com a qualidade*/
            if (random.nextDouble() < rejeicao) {
                produto.setStatus("REJEITADO");
                System.out.println("[ERRO]  Produto REJEITADO na inspeção de qualidade!");
            } else {
                produto.setStatus("APROVADO");
            }
        }
    }
}