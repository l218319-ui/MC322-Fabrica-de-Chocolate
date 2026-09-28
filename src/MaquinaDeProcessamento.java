public class MaquinaDeProcessamento extends Maquina {

    // Construtor:
    public MaquinaDeProcessamento(String nome, float custoOperacao, double capacidadeMaxima) {
        super(nome, capacidadeMaxima, 0.0f, custoOperacao);
    }

    // Métodos:
    public String getTipo() {
        return "Máquina de processamento";
    }

    public void processar(Produto produto) {
        if (!this.estaLigada()) {
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
            double chanceFalha = (100.0 - this.getSaude()) / 100.0;
            if (random.nextDouble() < chanceFalha) {
                produto.aumentarProbabilidadeFalha(0.03f);
            }
        }
    }
}