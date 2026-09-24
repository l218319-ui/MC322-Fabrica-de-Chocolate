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
            return;
        } else if (this.getSaude() <= 0) {
            System.out.println("[ERRO]  Não é possível inspecionar, pois a máquina " + getNome() + " está quebrada!");
            return;
        } else {
            if (verificarFalha()) {// checagem de falha da maquina
                produto.setStatus("REJEITADO");
                System.out.println("[ERRO]  Produto REJEITADO! (Falha na Máquina de Inspeção)");
            }

            //prob. de falha da maq. de inspeção:

            int chanceFalha = (100 - this.getSaude()) / 100;
            /*pega a qualidade e multiplica por um fator de 0.1 (se nn for assim o produto de alta qualidade tem 90% de
            chance de falha) aí a chance de falha fica proporcional com a qualidade*/
            float rejeicao = (produto.getQualidade() * 0.1f) + produto.getProbabilidadeFalhaAcumulada();
            if (random.nextDouble() < chanceFalha) {//baseado em saúde
                produto.setStatus("REJEITADO");
                System.out.println("[ERRO]  Produto REJEITADO (A saúde da máquina está baixa)!");
            } else if (random.nextDouble() < rejeicao) {//baseado em qualidade
                produto.setStatus("REJEITADO");
                System.out.println("[ERRO]  Produto REJEITADO na inspeção de qualidade!");
            } else {
                produto.setStatus("APROVADO");
            }

            // diminuir a saúde da máq!!!!!!!!!
            int dano = random.nextInt(1,4);
            aplicaDesgaste(dano);

        }
    }
}
//OBS: CONSERTAR OS STATUS PODICRÊ