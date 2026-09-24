public class MaquinaEmbaladora extends Maquina {

    // Construtor:
    public MaquinaEmbaladora(String nome, float custoOperacao, double capacidadeMaxima) {
        super(nome, capacidadeMaxima, 0.0f, custoOperacao);
    }

    public String getTipo() {
        return "Máquina de embalar chocolates";
    }

    public void processar(Produto produto) {
        if (!estaLigada()) {
            System.out.println("[ERRO]  Não é possível embalar, pois a máquina " + getNome() + " está desligada!");
            return;
        } else if (this.getSaude() <= 0) {
            System.out.println("[ERRO]  Não é possível embalar, pois a máquina " + getNome() + " está quebrada!");
            return;
        } else {
            produto.setStatus("Produto embalado");

            // diminuir a saúde da máq!!!!!!!!!
            int dano = random.nextInt(1,4);
            aplicaDesgaste(dano);

            // aumenta prob de falha baseada na saúde das máq.:
            int chanceFalha = (100 - this.getSaude()) / 100;
            if (random.nextDouble() < chanceFalha) {
                produto.aumentarProbabilidadeFalha(0.05f);
            }
        }
    }

}