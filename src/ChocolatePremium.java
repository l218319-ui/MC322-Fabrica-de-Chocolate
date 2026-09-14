public class ChocolatePremium extends Produto {

    // Construtor:
    public ChocolatePremium(int id, String nome) {
        super(id, nome, 9.0, 0.9f);
    }

    // Métodos:
    public void processar() {
        setStatus("Em processamento...");
    }

    public float calcularTempoProducao() {
        // retorna tempo em minutos
        return 3.0f;
    }

    public String getTipo() {
        return getNome() + " Premium Gourmet & Artesanal (estão te enganando pra tirar seu dinheiro, nada disso é real)";
    }
}
