public enum Cenario {
    // Constantes:
    IDEAL("IDEAL", 1000000.0, 0.2f, 0.2f),
    APOCALIPTICO("APOCALÍPTICO", 1000.0, 2.0f, 2.0f);

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
