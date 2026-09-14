public class MaquinaDeProcessamento extends Maquina {

    // Construtor:
    public MaquinaDeProcessamento(String nome, float custoOperacao, double capacidadeMaxima) {
        super(nome, capacidadeMaxima, 0.0f, custoOperacao);
    }

    public String getTipo() {
        return "Máquina de processamento";
    }

    public void processar(Produto produto) {
        if (!this.estaLigada()) {
            System.out.println("[ERRO]  Não é possível processar, pois a máquina está desligada!");
        } else {
            produto.processar();
            // aumenta prob de falha:
            if (random.nextDouble() < 0.09) {// a máquina de processamento tem 9% de chance de aumentar a prob. de falha
                produto.aumentarProbabilidadeFalha(0.05f);
            }
        }
    }

}
