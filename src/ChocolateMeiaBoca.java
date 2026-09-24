public class ChocolateMeiaBoca extends Produto {

    // Construtor:
    public ChocolateMeiaBoca(int id, String nome, String lote) {
        super(id, nome, 7.0, 0.7f, lote);
    }

    // Métodos:
    public void processar() {
        setStatus("Em processamento...");
    }

    public float calcularTempoProducao() {
        // retorna tempo em minutos
        return 2.0f;
    }

    public String getTipo() {
        return getNome() + " de média Qualidade (alma de sapatênis, uma média entre tênis e sapatilha)";
    }
}