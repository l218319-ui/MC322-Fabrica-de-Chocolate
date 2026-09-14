public class ChocolateSeboso extends Produto {

    // Construtor:
    public ChocolateSeboso(int id, String nome) {
        super(id, nome, 5.0, 0.5f);
    }

    // Métodos:
    public void processar() {
        setStatus("Em processamento...");
    }

    public float calcularTempoProducao() {
        // retorna tempo em minutos
        return 1.0f;
    }

    public String getTipo() {
        return getNome() + " Sebo Puro (que nem chocolate de guarda-chuvinha)";
    }
}