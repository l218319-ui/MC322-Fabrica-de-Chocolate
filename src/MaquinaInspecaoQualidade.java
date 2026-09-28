public class MaquinaInspecaoQualidade extends Maquina {

    // Construtor:
    public MaquinaInspecaoQualidade(String nome, float custoOperacao) {
        super(nome, 100.0, 0.04f, custoOperacao);
    }

    // Métodos:
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

            //prob. de falha da maq. de inspeção:
            double chanceFalha = (100.0 - this.getSaude()) / 100.0;
            float defeitoBase = produto.getQualidade() * 0.01f;
            float chanceDefeitoTotal = defeitoBase + produto.getProbabilidadeFalhaAcumulada();

            if (verificarFalha()) {// checagem de falha da maquina
                produto.setStatus("REJEITADO");
                System.out.println("[ERRO]  Produto REJEITADO! (Falha na Máquina de Inspeção)");
            } else if (this.getSaude() < 20 && random.nextDouble() < chanceFalha) {//baseado em saúde
                produto.setStatus("REJEITADO");
                System.out.println("[ERRO]  Produto REJEITADO (A saúde da máquina está baixa, faça reparos!)!");
            } else if (random.nextDouble() < chanceDefeitoTotal) {//baseado em qualidade
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