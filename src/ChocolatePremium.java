public class ChocolatePremium extends Produto {

    // Construtor:
    public ChocolatePremium(int id, String nome, String lote) {
        super(id, nome, 9.0, 0.9f, lote);
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