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
        } else if (this.getSaude() <= 0) {
            System.out.println("[ERRO]  Não é possível embalar, pois a máquina " + getNome() + " está quebrada!");
        } else {
            produto.setStatus("Produto embalado");
            // aumenta prob de falha:
            if (random.nextDouble() < 0.07) {// a máquina de embalar tem 7% de chance de aumentar a prob. de falha
                produto.aumentarProbabilidadeFalha(0.05f);
            }
            // diminuir a saúde da máq!!!!!!!!!
            int dano = random.nextInt(1,4);
            aplicaDesgaste(dano);
        }
    }

}