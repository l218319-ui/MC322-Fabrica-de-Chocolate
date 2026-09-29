public enum Cenario {
    // Constantes:
    IDEAL("IDEAL", 100000.0, 1, 1.0f),
    APOCALIPTICO("APOCALÍPTICO", 1000.0, 2, 2.0f);

    // Atributos:
    private final double orçamentoInicial;
    private final int desgaste;
    private final float falha;
    private final String nome;

    // Construtor:
    private Cenario(String nome, double orçamentoInicial, int desgaste, float falha) {
        this.orçamentoInicial = orçamentoInicial;
        this.desgaste = desgaste;
        this.falha = falha;
        this.nome = nome;
    }

    // Métodos:
    public double getOrçamentoInicial() {
        return orçamentoInicial;
    }

    public int getDesgaste() {
        return desgaste;
    }

    public float getFalha() {
        return falha;
    }

    public String getNome() {
        return nome;
    }
}
