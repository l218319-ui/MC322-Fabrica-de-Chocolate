public enum Cenario {
    // Constantes:
    IDEAL("IDEAL", 1000000.0, 0.04f, 0.04f),
    APOCALIPTICO("APOCALÍPTICO", 1000.0, 1.0f, 1.0f);

    // Atributos:
    private final double orçamentoInicial;
    private final float fatorDesgaste;
    private final float fatorFalha;
    private final String nome;

    // Construtor:
    private Cenario(String nome, double orçamentoInicial, float fatorDesgaste, float fatorFalha) {
        this.orçamentoInicial = orçamentoInicial;
        this.fatorDesgaste = fatorDesgaste;
        this.fatorFalha = fatorFalha;
        this.nome = nome;
    }

    // Métodos:
    public double getOrçamentoInicial() {
        return orçamentoInicial;
    }

    public float getFatorDesgaste() {
        return fatorDesgaste;
    }

    public float getFatorFalha() {
        return fatorFalha;
    }

    public String getNome() {
        return nome;
    }
}
