public class MaquinaDeProcessamento extends Maquina {

    // Construtor:
    public MaquinaDeProcessamento(String nome, float custoOperacao, double capacidadeMaxima) {
        super(nome, capacidadeMaxima, 0.0f, custoOperacao);
    }

    public String getTipo() {
        return "Máquina de processamento";
    }

    public void processar(Produto produto) {
        if (!this.estaLigada()) {//verifica se está ligada.
            System.out.println("[ERRO]  Não é possível processar, pois a máquina " + getNome() + " está desligada!");
        } else if (this.getSaude() <= 0) {
            System.out.println("[ERRO]  Não é possível processar, pois a máquina " + getNome() + " está quebrada!");            
        } else {
            produto.processar();
            // aumenta prob de falha:
            if (random.nextDouble() < 0.07) {// a máquina de processamento tem 9% de chance de aumentar a prob. de falha
                produto.aumentarProbabilidadeFalha(0.05f);
            }
            // diminuir a saúde da máq!!!!!!!!!

        }
    }

}