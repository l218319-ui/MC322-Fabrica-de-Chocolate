public class MaquinaDeProcessamento extends Maquina {

    // Construtor:
    public MaquinaDeProcessamento(String nome, float custoOperacao, double capacidadeMaxima) {
        super(nome, capacidadeMaxima, 0.0f, custoOperacao);
    }

    public String getTipo() {
        return "Máquina de processamento";
    }

    public void processar(Produto produto) {
        if (!this.estaLigada()) {// verifica se está ligada.
            System.out.println("[ERRO]  Não é possível processar, pois a máquina " + getNome() + " está desligada!");
            return;
        } else if (this.getSaude() <= 0) {
            System.out.println("[ERRO]  Não é possível processar, pois a máquina " + getNome() + " está quebrada!");
            return;
        } else {
            produto.processar();

            // diminuir a saúde da máq!!!!!!!!!
            int dano = random.nextInt(1, 4);
            aplicaDesgaste(dano);

            // aumenta prob de falha baseada na saúde das máq.:
            int chanceFalha = (100 - this.getSaude()) / 100;
            if (random.nextDouble() < chanceFalha) {
                produto.aumentarProbabilidadeFalha(0.05f);
            }
        }
    }

}